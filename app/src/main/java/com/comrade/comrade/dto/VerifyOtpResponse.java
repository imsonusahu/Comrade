package com.comrade.comrade.dto;

public class VerifyOtpResponse {


    private String message;
    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VerifyOtpResponse(String message, String type) {
        this.message = message;
        this.type = type;
    }
}
