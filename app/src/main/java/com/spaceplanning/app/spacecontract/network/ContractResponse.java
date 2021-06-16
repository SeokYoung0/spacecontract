package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class ContractResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String mesage;
}
