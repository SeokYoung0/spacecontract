package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttachedFileData {

    @SerializedName("files")
    public List<String> files;

    public AttachedFileData(List<String> files) {
        this.files = files;
    }
}
