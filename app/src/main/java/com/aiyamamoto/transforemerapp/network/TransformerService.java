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

    public static String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0cmFuc2Zvcm1lcnNJZCI6Ii1MTUwyVnAwNmtJX3NrZngxYnNWIiwiaWF0IjoxNTM2ODk0NTcyfQ.9GQC0p1KfdIFccPg1lhJoA3HK3oYQbyM96P8FD8l-yk";
    private static String header = "Bearer " + ACCESS_TOKEN + "";

    private static final String MULTIPART_DATA = "multipart/form-data";

    public static void getAllsparkToken(Callback<String> callback) {
        RetrofitBuilder.getTransformerApi().getAllsparkToken("application/json").enqueue(callback);
    }

    public static void createTransformer(CreateTransformerBody body, Callback<TransformerResponse> callback) {

        RetrofitBuilder.getTransformerApi().createTransformer(header, body).enqueue(callback);

    }

    public static void getTransformersList(Callback<TransformersList> callback) {

        RetrofitBuilder.getTransformerApi().getTransformersList(header).enqueue(callback);

    }
}
