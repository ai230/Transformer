package com.aiyamamoto.transforemerapp;

import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.TransformerApi;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * To test Retrofit2 mocking HTTP responses
 * {@link TransformerApi#getAllsparkToken(String)}
 *
 * Created by aiyamamoto on 2018-09-16.
 */
public class MockTransformerApiService implements TransformerApi {

    private final BehaviorDelegate<TransformerApi> delegate;

    public MockTransformerApiService(BehaviorDelegate<TransformerApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<String> getAllsparkToken(String accept) {
        String token = "faketoken";
        return delegate.returningResponse(token).getAllsparkToken("");
    }

    @Override
    public Call<TransformerResponse> createTransformer(String authorization, CreateTransformerBody body) {
        return null;
    }

    @Override
    public Call<TransformerResponse> editTransformer(String authorization, CreateTransformerBody body) {
        return null;
    }

    @Override
    public Call<TransformersList> getTransformersList(String authorization) {
        return null;
    }

    @Override
    public Call<Void> deleteTransformer(String authorization, String transformerId) {
        return null;
    }
}
