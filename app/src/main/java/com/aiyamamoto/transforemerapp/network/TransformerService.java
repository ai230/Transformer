package com.aiyamamoto.transforemerapp.network;


import retrofit2.Callback;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformerService {

    public static void getAllsparkToken(Callback<String> callback) {
        RetrofitBuilder.getTransformerApi().getAllsparkToken().enqueue(callback);
    }

}
