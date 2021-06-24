package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class ContractData {
    @SerializedName("company")
    private String company;

    @SerializedName("client")
    private String client;

    @SerializedName("companyEmail")
    private String companyEmail;

    @SerializedName("clientEmail")
    private String clientEmail;

    @SerializedName("companyPhone")
    private String companyPhone;

    @SerializedName("clientPhone")
    private String clientPhone;

    @SerializedName("formId")
    private String formId;

    @SerializedName("expirationDate")
    private String expirationDate;


    public ContractData(String company, String client, String companyEmail, String clientEmail, String companyPhone, String clientPhone, String formId, String expirationDate) {
        this.company = company;
        this.client = client;
        this.companyEmail = companyEmail;
        this.clientEmail = clientEmail;
        this.companyPhone = companyPhone;
        this.clientPhone = clientPhone;
        this.formId = formId;
        this.expirationDate = expirationDate;
    }
}