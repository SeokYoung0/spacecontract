package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class AttachedFileResponse {

    @SerializedName("fileName")
    public String fileName;
    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getFileName() {
        return fileName;
    }
}
