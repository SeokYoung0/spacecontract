package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

public class PreViewData {
    //String 값
    @SerializedName("formid")
    String formid;

    public PreViewData(String formid) {
        this.formid = formid;
    }
}
