package com.aiyamamoto.transforemerapp;

import com.aiyamamoto.transforemerapp.models.Error;
import com.aiyamamoto.transforemerapp.network.TransformerApi;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;

/**
 * To test Retrofit2 mocking HTTP responses.
 *
 * {@link TransformerApi#getAllsparkToken(String)}
 * {@link TransformerApi#createTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#editTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#deleteTransformer(String, String)}
 * {@link TransformerApi#getTransformersList(String)}
 *
 * Created by aiyamamoto on 2018-09-16.
 */

public class TransformerApiErrorResponse {

    private Error error;

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
