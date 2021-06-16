package com.spaceplanning.app.spacecontract.network;

import com.google.android.gms.auth.TokenData;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  ServiceApi {
    @POST("/send/contract/")
    Call<ContractResponse> userLogin(@Body ContractData data);
}