package com.sqlboaconnector.server.command;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;
import com.sqlboaconnector.server.ServerResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class RawExecCommand implements ServerCommand {

    @Override
    public ServerResponse execute(CommandContext context, JSONObject request) throws IOException, SQLException {

        String query = request.optString("query");

        context.dbProvider.open().execSQL(query);

        return new SuccessResponse();
    }
}
