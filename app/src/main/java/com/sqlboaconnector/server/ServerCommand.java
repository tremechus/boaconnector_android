package com.sqlboaconnector.server;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public interface ServerCommand {

    public void execute(CommandContext context, Hessian2Input request, Hessian2Output response) throws IOException, SQLException;
}
