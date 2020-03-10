package com.example.facey.models;

import com.google.gson.annotations.SerializedName;

public class ResponseResult {

    @SerializedName("success")
    Boolean success;

    @SerializedName("message")
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
