package com.example.facey.config;

import android.content.Context;
import android.util.Log;


import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Auth;
import com.example.facey.retrofit.AppAPIInterface;
import com.example.facey.retrofit.AppClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {
    public static DataManager dataManager = null;
    private AppAPIInterface appAPIInterface;
    private Context mContext;

    public static DataManager getDataManager() {
        if (dataManager == null)
            dataManager = new DataManager();

        return dataManager;
    }

    public void init(Context context) {
        appAPIInterface = AppClient.getAPiClient().create(AppAPIInterface.class);
        mContext = context;
    }

    public  void verifyEmail(HashMap<String, String> email, final RetrofitCallBack<Auth> retrofitCallBack){

        Call<Auth> responseCall = AppClient.getAPiClient().create(AppAPIInterface.class).verifyEmail(email);
        responseCall.enqueue((new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(response.isSuccessful())
                    retrofitCallBack.Success(response.body());
                else
                    retrofitCallBack.Failure("Something went wrong");
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                retrofitCallBack.Failure("Something went wrong");
            }
        }));

    }

//    public void userLogin(HashMap<String,String> loginParams, final RetrofitCallBack<String> retrofitCallBack){
//        Call<Auth> responseCall = AppClient.getAPiClient().create(AppAPIInterface.class)
//                .userLogin(loginParams);
//        responseCall.enqueue(new Callback<Auth>() {
//            @Override
//            public void onResponse(Call<Auth> call, Response<Auth> response) {
//                if (response.isSuccessful()){
//                    retrofitCallBack.Success(response.body().getAccessToken());
//
//                } else {
//                    retrofitCallBack.Failure("some error happens !");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Auth> call, Throwable t) {
//                retrofitCallBack.Failure("some error happens !");
//            }
//        });
//    }
}
