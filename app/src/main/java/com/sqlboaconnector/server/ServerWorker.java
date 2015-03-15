package com.sqlboaconnector.server;

import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.api.DatabaseProvider;
import com.sqlboaconnector.server.command.ErrorResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {
    private Socket socket;
    private DatabaseProvider dbProvider;
    private CommandMap commandMap = new CommandMap();

    public ServerWorker(DatabaseProvider dbProvider, Socket socket) {
        this.socket = socket;
        this.dbProvider = dbProvider;
    }

    @Override
    public void run() {
        Hessian2Output out = null;
        Hessian2Input in;

        try {
            out = new Hessian2Output(socket.getOutputStream());
            in = new Hessian2Input(socket.getInputStream());

            CommandContext context = new CommandContext();
            context.dbProvider = dbProvider;

            JSONObject request = new JSONObject(in.readString());

            ServerCommand command = commandMap.get(request.optString("command"));
            if (command == null) {
                out.writeString(new ErrorResponse(ErrorResponse.ErrorCode.MISSINGCOMMAND, null).toJson());
                return;
            }

            ServerResponse response = command.execute(context, request);
            out.writeString(response.toJson());
        } catch (Exception e) {
            try {
                out.writeString(new ErrorResponse(ErrorResponse.ErrorCode.MISSINGCOMMAND, e.getMessage()).toJson());
            } catch (IOException e2) {
                // TODO: better response?
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    out.flush();
                    socket.close();
                } catch (IOException e) {
                    // TODO: More details?
                    e.printStackTrace();
                }
            }
        }

    }

}
