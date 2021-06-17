package com.spaceplanning.app.spacecontract.network;

import com.google.android.gms.auth.TokenData;


import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface  ServiceApi {
    @POST("/send/contract/")
    Call<ContractResponse> userLogin(@Body ContractData data);

    @Multipart
    @POST("/api/upload/")
    Call<Result> uploadImage(@Part MultipartBody.Part File);
}