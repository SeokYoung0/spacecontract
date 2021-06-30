package com.spaceplanning.app.spacecontract.network;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

public class AttachedFileData {

    private String fileName;
    private String fileType;
    private Uri fileUri;

    public AttachedFileData(String fileName, String fileType, Uri fileUri) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUri = fileUri;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public Uri getFileUri() {
        return fileUri;
    }
}
