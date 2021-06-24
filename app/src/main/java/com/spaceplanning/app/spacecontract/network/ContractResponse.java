package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class ContractResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
