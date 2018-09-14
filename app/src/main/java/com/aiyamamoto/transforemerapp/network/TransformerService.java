package com.aiyamamoto.transforemerapp.network;


import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformerService {

    public static String ACCESS_TOKEN;

    public static void getAllsparkToken(Callback<String> callback) {
        RetrofitBuilder.getTransformerApi().getAllsparkToken("application/json").enqueue(callback);
    }

    public static void createTransformer(CreateTransformerBody body, Callback<TransformerResponse> callback) {

        RetrofitBuilder.getTransformerApi().createTransformer("Bearer " + ACCESS_TOKEN, body).enqueue(callback);

    }

    public static void getTransformersList(Callback<TransformersList> callback) {

        RetrofitBuilder.getTransformerApi().getTransformersList("Bearer " + ACCESS_TOKEN).enqueue(callback);

    }
}
