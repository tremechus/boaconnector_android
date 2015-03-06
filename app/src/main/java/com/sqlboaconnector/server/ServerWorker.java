package com.sqlboaconnector.server;

import android.util.Log;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sqlboaconnector.api.DatabaseProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
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
        try {
            Hessian2Output out = new Hessian2Output(socket.getOutputStream());
            Hessian2Input in = new Hessian2Input(socket.getInputStream());

            CommandContext context = new CommandContext();
            context.dbProvider = dbProvider;

            while (true) {
                Log.d("####", "Obtaining next command ...");
                String commandKey = in.readString();
                Log.d("####", "\t'" + commandKey + "'");

                ServerCommand command = commandMap.get(commandKey);
                if (command == null) {
                    Log.d("####", "Could not find command for key '" + commandKey + "'");
                    continue;
                }
                Log.d("####", "\tExecuting command");

                command.execute(context, in, out);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("####", "Remote connection closed");
        }

    }

}
