package com.spaceplanning.app.spacecontract.network;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

public class AttachedFileData {

    @SerializedName("files")
    public HashMap<String, RequestBody> files;

    public AttachedFileData(HashMap<String, RequestBody> files) {
        this.files = files;
    }
}
