package com.sqlboaconnector.server;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerResponse {

    public enum Status {
        ERROR(0),
        OK(1)
        ;

        private int code;
        private Status(int code) {
            this.code = code;
        }
    }

    private Status status;

    public ServerResponse(Status status) {
        this.status = status;
    }

    public String toJson() {

        JSONObject o = new JSONObject();
        try {
            applyJson(o);
        } catch (JSONException e) {
            // This should never happen
            // TODO: better logging
            e.printStackTrace();
            return "{'status':0,'code':3}";
        }

        return o.toString();
    }

    protected void applyJson(JSONObject o) throws JSONException {
        o.putOpt("status", status.code);
    }
}
