package com.aiyamamoto.transforemerapp;

import com.aiyamamoto.transforemerapp.models.Error;
import com.aiyamamoto.transforemerapp.network.TransformerApi;

/**
 * To test Retrofit2 mocking HTTP responses
 * {@link TransformerApi#getAllsparkToken(String)}
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
