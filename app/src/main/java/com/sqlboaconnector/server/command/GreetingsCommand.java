package com.sqlboaconnector.server.command;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.Hessian2StreamingInput;
import com.sqlboaconnector.server.CommandContext;
import com.sqlboaconnector.server.ServerCommand;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class GreetingsCommand implements ServerCommand {

    @Override
    public void execute(CommandContext context, Hessian2Input request, Hessian2Output response) throws IOException, SQLException {

        response.writeString("ok");
    }
}
