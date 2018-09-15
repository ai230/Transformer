package com.aiyamamoto.transforemerapp.network;

import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public interface TransformerApi {

    @GET("allspark")
    Call<String> getAllsparkToken(@Header("accept") String accept);

    @POST("transformers")
    Call<TransformerResponse> createTransformer(@Header("Authorization")String authorization,
                                                @Body CreateTransformerBody body);

    @PUT("transformers")
    Call<TransformerResponse> editTransformer(@Header("Authorization")String authorization,
                                                @Body CreateTransformerBody body);

    @GET("transformers")
    Call<TransformersList> getTransformersList(@Header("Authorization")String authorization);

    @DELETE("transformers/{transformerId}")
    Call<Void> deleteTransformer(@Header("Authorization")String authorization,
                                 @Path("transformerId") String transformerId);

}
