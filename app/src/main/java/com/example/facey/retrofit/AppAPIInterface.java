package com.example.facey.retrofit;

import com.example.facey.models.Auth;
import com.example.facey.models.Batch;
import com.example.facey.models.Branch;
import com.example.facey.models.Subject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppAPIInterface {

    @FormUrlEncoded()
    @POST("auth/verify/")
    Call<Auth> verifyEmail(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded()
    @POST("user/verify")
    Call<Auth> verifyUser(@FieldMap HashMap<String, String> params);




    @GET("college/branchs/")
    Call<ArrayList<Branch>> getBranches();


    @GET("college/batchs/{branchId}")
    Call<ArrayList<Batch>> getBatch(@Path("branchId") int branchId);


    @GET("college/subjects/{branchId}")
    Call<ArrayList<Subject>> getSubjects(@Path("branchId") int branchId);

}
