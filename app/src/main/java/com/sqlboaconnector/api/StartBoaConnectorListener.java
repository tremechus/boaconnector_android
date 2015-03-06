package com.sqlboaconnector.api;

import android.app.Activity;
import android.view.View;

public class StartBoaConnectorListener implements View.OnClickListener {

    private Activity activity;
    private BoaConnector connector;
    private DatabaseProvider dbProvider;

    public StartBoaConnectorListener(Activity activity, BoaConnector connector, DatabaseProvider dbProvider) {
        this.activity = activity;
        this.connector = connector;
        this.dbProvider = dbProvider;
    }

    @Override
    public void onClick(View v) {

        connector.startServer(dbProvider);
        connector.showPairDialog(activity);
    }
}
