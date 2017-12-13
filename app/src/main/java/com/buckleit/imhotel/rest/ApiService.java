
package com.buckleit.imhotel.rest;

import com.buckleit.imhotel.requestModel.LoginDetails;
import com.buckleit.imhotel.responseModel.ApiResponse;
import com.buckleit.imhotel.responseModel.LogoutApiResponse;
import com.buckleit.imhotel.responseModel.UpdateApiResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiService {


    @POST("api/login")
    Call<ApiResponse> loginUser(@Body LoginDetails loginDetails);

    @GET("api/logout")
    Call<LogoutApiResponse> logout(@Header("token") String token);


    @Multipart
    @POST("api/booking/4/bookingImages")
    Call<UpdateApiResponse> uploadFile(@Part MultipartBody.Part file);
}


