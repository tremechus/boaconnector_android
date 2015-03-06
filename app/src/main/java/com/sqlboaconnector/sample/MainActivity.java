package com.sqlboaconnector.sample;

import android.app.Activity;
import android.os.Bundle;

import com.sqlboaconnector.R;
import com.sqlboaconnector.api.BoaConnector;
import com.sqlboaconnector.api.StartBoaConnectorListener;

public class MainActivity extends Activity {

    private BoaConnector boaConnector = new BoaConnector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_samplemain);

        findViewById(R.id.pairButton).setOnClickListener(new StartBoaConnectorListener(this, boaConnector, new SampleDatabaseProvider(this)));
    }
}
