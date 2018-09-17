package com.aiyamamoto.transforemerapp;

import android.util.Log;

import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.models.Error;
import com.aiyamamoto.transforemerapp.network.TransformerApi;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/**
 * Setting error data for unit tests Retrofit2 mocking HTTP responses.
 *
 * {@link TransformerApi#getAllsparkToken(String)}
 * {@link TransformerApi#createTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#editTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#deleteTransformer(String, String)}
 * {@link TransformerApi#getTransformersList(String)}
 *
 * Created by aiyamamoto on 2018-09-16.
 */
public class MockFailedTransformerApiService implements TransformerApi {

    private static final String TAG = "MockFailed";
    private final BehaviorDelegate<TransformerApi> delegate;

    public MockFailedTransformerApiService(BehaviorDelegate<TransformerApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<String> getAllsparkToken(String accept) {
        Error error = new Error();
        error.setCode(404);
        error.setMessage("Request Not Found");

        TransformerApiErrorResponse transformerApiErrorResponse = new TransformerApiErrorResponse();
        transformerApiErrorResponse.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(transformerApiErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).getAllsparkToken("");
        } catch (JsonProcessingException e) {
            Log.e(TAG, "JSON Processing exception:",e);
            return Calls.failure(e);
        }
    }

    @Override
    public Call<TransformerResponse> createTransformer(String authorization, CreateTransformerBody body) {
        Error error = new Error();
        error.setCode(404);
        error.setMessage("Request Not Found");

        TransformerApiErrorResponse transformerApiErrorResponse = new TransformerApiErrorResponse();
        transformerApiErrorResponse.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(transformerApiErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).createTransformer(authorization, body);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "JSON Processing exception:",e);
            return Calls.failure(e);
        }
    }

    @Override
    public Call<TransformerResponse> editTransformer(String authorization, CreateTransformerBody body) {
        Error error = new Error();
        error.setCode(404);
        error.setMessage("Request Not Found");

        TransformerApiErrorResponse transformerApiErrorResponse = new TransformerApiErrorResponse();
        transformerApiErrorResponse.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(transformerApiErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).editTransformer(authorization, body);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "JSON Processing exception:",e);
            return Calls.failure(e);
        }
    }

    @Override
    public Call<TransformersList> getTransformersList(String authorization) {

        Error error = new Error();
        error.setCode(404);
        error.setMessage("Request Not Found");

        TransformerApiErrorResponse transformerApiErrorResponse = new TransformerApiErrorResponse();
        transformerApiErrorResponse.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(transformerApiErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).getTransformersList(authorization);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "JSON Processing exception:",e);
            return Calls.failure(e);
        }
    }

    @Override
    public Call<Void> deleteTransformer(String authorization, String transformerId) {

        Error error = new Error();
        error.setCode(404);
        error.setMessage("Request Not Found");

        TransformerApiErrorResponse transformerApiErrorResponse = new TransformerApiErrorResponse();
        transformerApiErrorResponse.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(transformerApiErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).deleteTransformer(authorization, transformerId);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "JSON Processing exception:",e);
            return Calls.failure(e);
        }
    }
}
