package com.aiyamamoto.transforemerapp.network;

import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public interface TransformerApi {

    @GET("allspark")
    Call<String> getAllsparkToken(@Header("accept") String accept);

    @POST("transformers")
    Call<TransformerResponse> createTransformer(@Header("Authorization")String authorization,
                                                @Body CreateTransformerBody body);

    @GET("transformers")
    Call<TransformersList> getTransformersList(@Header("Authorization")String authorization);


}
