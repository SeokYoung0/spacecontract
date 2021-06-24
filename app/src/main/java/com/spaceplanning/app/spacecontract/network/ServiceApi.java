package com.spaceplanning.app.spacecontract.network;


import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface  ServiceApi {

    @Multipart
    @POST("/api/upload/")
    Call<Result> uploadImage(@Part MultipartBody.Part File);

    @POST("/api/eform/send/contract/")
    Call<ContractResponse> sendContract(@Body ContractData data);

    @POST("/upload-multi")
    Call<AttachedFileResponse> attachedFiles(@Body AttachedFileData data);

    @POST("/preview")
    Call<PreViewRespone> FormUrl(@Body PreViewData data);
}