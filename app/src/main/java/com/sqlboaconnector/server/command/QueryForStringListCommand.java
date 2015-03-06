package com.sqlboaconnector.server.command;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trevor on 3/6/2015.
 */
public class QueryForStringListCommand implements ServerCommand {

    @Override
    public void execute(CommandContext context, Hessian2Input request, Hessian2Output response) throws IOException, SQLException {

        String query = request.readString();
        Log.d("####", "Query: " + query);

        SQLiteDatabase db = context.dbProvider.open();
        Log.d("####", "Got db: " + db);

        List<String> results = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, new String[]{});
        while (cursor.moveToNext()) {
            String value = cursor.getString(0);
            Log.d("####", "\tFound: " + value);
            results.add(value);
        }

        db.close();

        Log.d("####", "\twriting response");
        response.writeObject(results);
        Log.d("####", "\tall done");
    }
}
