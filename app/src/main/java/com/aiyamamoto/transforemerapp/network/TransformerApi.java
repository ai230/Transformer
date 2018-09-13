package com.aiyamamoto.transforemerapp.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public interface TransformerApi {

    @GET("allspark")
    Call<String> getAllsparkToken();

}
