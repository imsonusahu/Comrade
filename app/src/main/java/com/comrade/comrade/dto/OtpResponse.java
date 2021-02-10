package com.comrade.comrade.dto;

public class OtpResponse {


    private String status;
    private String otp;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OtpResponse(String status, String otp, String message) {
        this.status = status;
        this.otp = otp;
        this.message = message;
    }
}
