package com.sqlboaconnector.server.command;

import android.database.Cursor;
import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import sqlboa.model.ResultRow;
import sqlboa.model.StatementResult;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;
import com.sqlboaconnector.server.ServerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class QueryCommand implements ServerCommand {

    @Override
    public ServerResponse execute(CommandContext context, JSONObject request) throws IOException, SQLException {

        String query = request.optString("query");
        JSONArray paramList = request.optJSONArray("params");
        int page = request.optInt("page", 0);
        int perPage = request.optInt("perPage", 20);

        String[] paramArray = new String[paramList.length()];
        for (int i = 0; i < paramArray.length; i++) {
            paramArray[i] = paramList.optString(i);
        }
        Cursor cursor = context.dbProvider.open().rawQuery(query, paramArray);

        // Columns
        Response response = new Response();
        for (String colName : cursor.getColumnNames()) {
            response.columnNames.put(colName);
        }

        // TODO: Skip ahead to current page

        // Grab the current page of data
        int rowCount = 0;
        for (; rowCount < perPage && cursor.moveToNext(); rowCount++) {
            JSONArray colData = new JSONArray();
            for (int j = 0; j < response.columnNames.length(); j++) {
                // TODO: Handle different kinds of data
                colData.put(cursor.getString(j));
            }

            response.rows.put(colData);
        }

        // Skip to the end of the cursor to get total count
        while (cursor.moveToNext()) {
            rowCount++;
        }

        response.totalCount = rowCount;

        return response;
    }

    private class Response extends SuccessResponse {

        JSONArray columnNames = new JSONArray();
        JSONArray rows = new JSONArray();
        int totalCount;

        @Override
        protected void applyJson(JSONObject o) throws JSONException {
            super.applyJson(o);

            o.put("columns", columnNames);
            o.put("totalCount", totalCount);
            o.put("rows", rows);

        }
    }
}
