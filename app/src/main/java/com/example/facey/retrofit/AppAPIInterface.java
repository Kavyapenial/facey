package com.example.facey.retrofit;

import com.example.facey.models.Auth;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppAPIInterface {

    @FormUrlEncoded()
    @POST("auth/verify/")
    Call<Auth> verifyEmail(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded()
    @POST("user/verify")
    Call<Auth> verifyUser(@FieldMap HashMap<String, String> params);


}
