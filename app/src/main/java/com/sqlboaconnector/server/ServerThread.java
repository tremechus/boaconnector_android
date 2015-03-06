package com.sqlboaconnector.server;

import android.util.Log;

import com.sqlboaconnector.api.BoaConnector;
import com.sqlboaconnector.api.DatabaseProvider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private static final String TAG = ServerThread.class.getSimpleName();

    private int portNumber;
    private boolean stop = false;
    private ServerSocket serverSocket;
    private DatabaseProvider dbProvider;
    private BoaConnector connector;

    public ServerThread(BoaConnector connector, DatabaseProvider dbProvider, int portNumber) {
        this.portNumber = portNumber;
        this.dbProvider = dbProvider;
        this.connector = connector;
    }

    public void shutdown() {
        stop = true;

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Closing socket", e);
            }
        }
    }

    @Override
    public void run() {

        Log.d("####", "Listening for connection");
        try {
            serverSocket = new ServerSocket(portNumber);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Log.d("####", "Got connection");
                    new Thread(new ServerWorker(dbProvider, clientSocket)).start();
                } catch (Exception e) {
                    Log.e(TAG, "Fail to accept connection", e);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Fail to start server", e);
        }

    }
}
