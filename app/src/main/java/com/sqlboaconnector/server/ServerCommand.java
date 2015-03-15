package com.sqlboaconnector.server;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.server.CommandContext;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public interface ServerCommand {

    public abstract ServerResponse execute(CommandContext context, JSONObject request) throws IOException, SQLException;

}
