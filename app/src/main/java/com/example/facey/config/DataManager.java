package com.example.facey.config;

import android.content.Context;
import android.util.Log;


import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Auth;
import com.example.facey.models.Batch;
import com.example.facey.models.Branch;
import com.example.facey.models.Subject;
import com.example.facey.retrofit.AppAPIInterface;
import com.example.facey.retrofit.AppClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
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


    public void getBranchs(final RetrofitCallBack<ArrayList<Branch>> retrofitCallBack){
        Call<ArrayList<Branch>>  responseCall =  appAPIInterface.getBranches();


        responseCall.enqueue(new Callback<ArrayList<Branch>>() {
            @Override
            public void onResponse(Call<ArrayList<Branch>> call, Response<ArrayList<Branch>> response) {

                if(response.isSuccessful()){
                    retrofitCallBack.Success(response.body());
                }else{
                    retrofitCallBack.Failure("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Branch>> call, Throwable t) {
                retrofitCallBack.Failure("Something went wrong");
            }
        });



    }

    public void getBatch(int branchId, final RetrofitCallBack<ArrayList<Batch>> retrofitCallBack){
        Call<ArrayList<Batch>>  responseCall =  appAPIInterface.getBatch(branchId);
        responseCall.enqueue(new Callback<ArrayList<Batch>>() {
            @Override
            public void onResponse(Call<ArrayList<Batch>> call, Response<ArrayList<Batch>> response) {
                if(response.isSuccessful()){
                    retrofitCallBack.Success(response.body());
                }else{
                    retrofitCallBack.Failure("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Batch>> call, Throwable t) {
                retrofitCallBack.Failure("Something went wrong");
            }
        });


    }

    public void getSubjects(int branchId, final RetrofitCallBack<ArrayList<Subject>> retrofitCallBack) {
        Call<ArrayList<Subject>> responseCall = appAPIInterface.getSubjects(branchId);
        responseCall.enqueue(new Callback<ArrayList<Subject>>() {
            @Override
            public void onResponse(Call<ArrayList<Subject>> call, Response<ArrayList<Subject>> response) {
                if (response.isSuccessful()) {
                    retrofitCallBack.Success(response.body());
                } else {
                    retrofitCallBack.Failure("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
                retrofitCallBack.Failure("Something went wrong");
            }
        });
    }



    }
