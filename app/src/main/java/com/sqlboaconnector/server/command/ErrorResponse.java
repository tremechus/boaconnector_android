package com.sqlboaconnector.server.command;

import com.sqlboaconnector.server.ServerResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorResponse extends ServerResponse {

    public enum ErrorCode {
        UNKNOWN(0),
        NOTCONNECTED(1),
        REQTOOLARGE(2),
        INTERNAL(3),
        MISSINGCOMMAND(4)
        ;

        private int code;
        private ErrorCode(int code) {
            this.code = code;
        }
    }

    private ErrorCode errorCode;
    private String errorMessage;

    public ErrorResponse(ErrorCode code, String message) {
        super(Status.ERROR);
        this.errorCode = code != null ? code : ErrorCode.UNKNOWN;
        this.errorMessage = message;
    }

    @Override
    protected void applyJson(JSONObject o) throws JSONException {
        super.applyJson(o);

        o.putOpt("message", errorMessage);
        o.put("code", errorCode.code);
    }
}
