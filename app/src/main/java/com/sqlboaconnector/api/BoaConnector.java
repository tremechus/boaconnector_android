package com.sqlboaconnector.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqlboaconnector.R;
import com.sqlboaconnector.server.ServerThread;
import com.sqlboaconnector.util.NetUtil;

import java.util.List;

public class BoaConnector {

    private static final String TAG = BoaConnector.class.getSimpleName();

    private ServerThread serverThread;

    public void startServer(DatabaseProvider dbProvider) {
        if (serverThread != null && serverThread.isAlive()) {
            return;
        }

        serverThread = new ServerThread(this, dbProvider, 1234);
        serverThread.start();
    }

    public void stopServer() {
        if (serverThread == null || !serverThread.isAlive()) {
            return;
        }

        serverThread.shutdown();
    }

    public void showPairDialog(Activity context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_pair, null);

        List<String> possibleIpList = NetUtil.getAvailableIP();
        StringBuilder builder = new StringBuilder();
        for (String address : possibleIpList) {
            builder.append(address).append("\n");
        }
        ((TextView)view.findViewById(R.id.ipListLabel)).setText(builder.toString());

        view.findViewById(R.id.connectionRequestContainer).setVisibility(View.GONE);

        alertBuilder.setView(view);
        alertBuilder.create().show();
    }

}
