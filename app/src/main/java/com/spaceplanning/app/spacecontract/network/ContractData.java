package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class ContractData {
    @SerializedName("contractor")
    private String contractor;

    @SerializedName("client")
    private String client;

    @SerializedName("contractorEmail")
    private String contractorEmail;

    @SerializedName("clientEmail")
    private String clientEmail;

    @SerializedName("contractorPhone")
    private String contractorPhone;

    @SerializedName("clientPhone")
    private String clientPhone;

    @SerializedName("formId")
    private String formId;

    @SerializedName("expirationDate")
    private String expirationDate;

}