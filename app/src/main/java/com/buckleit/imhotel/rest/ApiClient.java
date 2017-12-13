
package com.buckleit.imhotel.rest;


import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "BASE_URL";

    private static ApiService apiService;
    private static Typeface client;


    public static ApiService getInstance() {

        if (apiService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = getOkHttpClient(logging);
            Retrofit retrofit = getRetrofit(client);
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    @NonNull
    private static Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getOkHttpClient(HttpLoggingInterceptor logging) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = getDefaultRequest(original);
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .addInterceptor(logging)
                .build();
    }


    private static Request getDefaultRequest(Request original) {
        Request request = original.newBuilder().
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json")
                // addHeader("access-key",access_key)
                .build();
        return request;
    }


    public static Typeface getClient() {
        return client;
    }
}