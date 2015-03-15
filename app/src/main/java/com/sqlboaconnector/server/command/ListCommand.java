package com.sqlboaconnector.server.command;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;
import com.sqlboaconnector.server.ServerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trevor on 3/6/2015.
 */
public class ListCommand implements ServerCommand {

    @Override
    public ServerResponse execute(CommandContext context, JSONObject request) throws IOException, SQLException {

        String query = request.optString("query");

        SQLiteDatabase db = context.dbProvider.open();

        Response response = new Response();

        Cursor cursor = db.rawQuery(query, new String[]{});
        while (cursor.moveToNext()) {
            String value = cursor.getString(0);
            response.results.put(value);
        }

        db.close();

        return response;
    }

    private class Response extends SuccessResponse {
        JSONArray results = new JSONArray();

        @Override
        protected void applyJson(JSONObject o) throws JSONException {
            super.applyJson(o);

            o.put("list", results);
        }
    }
}
