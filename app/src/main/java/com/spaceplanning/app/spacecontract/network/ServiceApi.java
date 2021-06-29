package com.spaceplanning.app.spacecontract.network;


import java.util.HashMap;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface  ServiceApi {

    @Multipart
    @POST("/api/upload/")
    Call<Result> uploadImage(@Part MultipartBody.Part File);

    @POST("/api/eform/send/contract/")
    Call<ContractResponse> sendContract(@Body ContractData data);

    @Multipart
    @POST("/upload-multi")
    Call<AttachedFileResponse> attachedFiles(@PartMap HashMap<String, RequestBody> files);

    @Multipart
    @POST("/upload-multi/")
    Call<AttachedFileResponse> postAttachment(@Part MultipartBody.Part files);

    @POST("/preview")
    Call<PreViewRespone> FormUrl(@Body PreViewData data);
}