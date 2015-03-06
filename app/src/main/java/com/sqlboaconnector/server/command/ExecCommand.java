package com.sqlboaconnector.server.command;

import android.database.Cursor;
import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import sqlboa.model.ResultRow;
import sqlboa.model.StatementResult;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by trevor on 3/6/2015.
 */
public class ExecCommand implements ServerCommand {

    @Override
    public void execute(CommandContext context, Hessian2Input request, Hessian2Output response) throws IOException, SQLException {

        Log.d("####", "exec: reading parameters");
        String query = request.readString();
        List<String> paramList = (List<String>) request.readObject();
        int pageSize = request.readInt();
        Log.d("####", "\tquery: " + query);
        Log.d("####", "\tparam: " + paramList.size());
        Log.d("####", "\tpages: " + pageSize);

        Cursor cursor = context.dbProvider.open().rawQuery(query, paramList.toArray(new String[paramList.size()]));

        String[] colNames = cursor.getColumnNames();
        StatementResult result = new StatementResult(colNames);
        Log.d("####", "Col names: " + Arrays.toString(colNames));

        // TODO: Skip ahead to current page

        // Grab the current page of data
        int rowCount = 0;
        for (; rowCount < pageSize && cursor.moveToNext(); rowCount++) {
            Object[] colData = new Object[colNames.length];
            for (int j = 0; j < colData.length; j++) {
                // TODO: Handle different kinds of data
                colData[j] = cursor.getString(j);
            }
            ResultRow row = new ResultRow(rowCount, colData);

            result.add(row);
        }
        Log.d("####", "Done reading rows");

        // Skip to the end of the cursor to get total count
        while (cursor.moveToNext()) {
            rowCount++;
        }
        Log.d("####", "Done finding total count");

        result.setTotalCount(rowCount);

        Log.d("####", "Writing response");
        response.writeObject(result);
    }
}
