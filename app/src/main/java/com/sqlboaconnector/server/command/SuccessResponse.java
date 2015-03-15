package com.sqlboaconnector.server.command;

import com.sqlboaconnector.server.ServerResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class SuccessResponse extends ServerResponse {

    public SuccessResponse() {
        super(Status.OK);
    }

}
