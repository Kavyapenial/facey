package com.example.facey.retrofit;

import android.text.TextUtils;

import com.example.facey.config.Session;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {

    public static final String MASTEERURL = "http://192.168.1.5:8000/";

    public static Retrofit retrofit = null;

    public static Retrofit getAPiClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(15, TimeUnit.SECONDS);
        httpClient.readTimeout(15, TimeUnit.SECONDS);
        httpClient.writeTimeout(15, TimeUnit.SECONDS);

        if (!TextUtils.isEmpty(Session.getAccessToken())) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .header("Authorization", Session.getAccessToken())
                            .build();

                    Response response = chain.proceed(request);
                    String rawJson = response.body().string();

                    /* Recreating response before returning because response body can be read only once */
                    return response.newBuilder()
                            .body(okhttp3.ResponseBody.create(response.body().contentType(), rawJson)).build();
                }
            });
        }
        httpClient.addInterceptor(interceptor);

        httpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });


//        if (retrofit == null) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MASTEERURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
//        }


        return retrofit;
    }

}
