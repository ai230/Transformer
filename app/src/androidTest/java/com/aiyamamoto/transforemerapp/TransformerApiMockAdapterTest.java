package com.aiyamamoto.transforemerapp;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.TransformerApi;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Unit test Retrofit2 mocking HTTP responses
 *
 * {@link TransformerApi#getAllsparkToken(String)}
 * {@link TransformerApi#createTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#editTransformer(String, CreateTransformerBody)}
 * {@link TransformerApi#deleteTransformer(String, String)}
 * {@link TransformerApi#getTransformersList(String)}
 * Created by aiyamamoto on 2018-09-16.
 */

public class TransformerApiMockAdapterTest extends InstrumentationTestCase {

    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        retrofit = new Retrofit.Builder().baseUrl("http://test.com")
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @SmallTest
    public void testRandomTokenRetrieval() throws Exception {
        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        TransformerApi mockApi = new MockTransformerApiService(delegate);

        String s = "faketoken";
        //Actual test
        Call<String> token = mockApi.getAllsparkToken("");
        Response<String> tokenResponse = token.execute();

        //Asserting response
        Assert.assertTrue(tokenResponse.isSuccessful());
        Assert.assertEquals(s, tokenResponse.body());

    }

    @SmallTest
    public void testFailedTokenRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockService = new MockFailedTransformerApiService(delegate);

        //Actual Test
        Call<String> token = mockService.getAllsparkToken("");
        Response<String> tokenResponse = token.execute();
        Assert.assertFalse(tokenResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(tokenResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, tokenResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }

    @Test
    public void testCreateTransformerRetrieval() throws Exception {
        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        TransformerApi mockApi = new MockTransformerApiService(delegate);

        CreateTransformerBody createTransformerBody =
                new CreateTransformerBody("0", "test", "A",2,5,7,9,2,5,6,8);


        TransformerResponse transformerResponse = new TransformerResponse("0", "test", "A",2,5,7,9,2,5,6,8, "url");

        //Actual test
        Call<TransformerResponse> responseCall = mockApi.createTransformer("bear token", createTransformerBody);
        Response<TransformerResponse> responseResponse = responseCall.execute();

        //Asserting response
        Assert.assertTrue(responseResponse.isSuccessful());
        Assert.assertEquals(transformerResponse.getId(),responseResponse.body().getId());
        Assert.assertEquals(transformerResponse.getName(),responseResponse.body().getName());
        Assert.assertEquals(transformerResponse.getTeam(),responseResponse.body().getTeam());
        Assert.assertEquals(transformerResponse.getStrength(),responseResponse.body().getStrength());
        Assert.assertEquals(transformerResponse.getIntelligence(),responseResponse.body().getIntelligence());
        Assert.assertEquals(transformerResponse.getSpeed(),responseResponse.body().getSpeed());
        Assert.assertEquals(transformerResponse.getSpeed(),responseResponse.body().getSpeed());
        Assert.assertEquals(transformerResponse.getEndurance(),responseResponse.body().getEndurance());
        Assert.assertEquals(transformerResponse.getRank(),responseResponse.body().getRank());
        Assert.assertEquals(transformerResponse.getCourage(),responseResponse.body().getCourage());
        Assert.assertEquals(transformerResponse.getFirepower(),responseResponse.body().getFirepower());
        Assert.assertEquals(transformerResponse.getSkill(),responseResponse.body().getSkill());
    }

    @Test
    public void testFailedCreateTransformerRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockService = new MockFailedTransformerApiService(delegate);

        CreateTransformerBody createTransformerBody =
                new CreateTransformerBody("0", "test", "A",2,5,7,9,2,5,6,8);

        //Actual Test
        Call<TransformerResponse> responseCall = mockService.createTransformer("bear token", createTransformerBody);
        Response<TransformerResponse> responseResponse = responseCall.execute();
        Assert.assertFalse(responseResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(responseResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, responseResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }

    @Test
    public void testEditTransformerRetrieval() throws Exception {
        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        TransformerApi mockApi = new MockTransformerApiService(delegate);

        CreateTransformerBody createTransformerBody =
                new CreateTransformerBody("0", "test", "A",2,5,7,9,2,5,6,8);

        TransformerResponse transformerResponse = new TransformerResponse("0", "test", "A",2,5,7,9,2,5,6,8, "url");

        //Actual test
        Call<TransformerResponse> responseCall = mockApi.editTransformer("bear token", createTransformerBody);
        Response<TransformerResponse> responseResponse = responseCall.execute();

        //Asserting response
        Assert.assertTrue(responseResponse.isSuccessful());
        Assert.assertEquals(transformerResponse.getId(),responseResponse.body().getId());
        Assert.assertEquals(transformerResponse.getName(),responseResponse.body().getName());
        Assert.assertEquals(transformerResponse.getTeam(),responseResponse.body().getTeam());
        Assert.assertEquals(transformerResponse.getStrength(),responseResponse.body().getStrength());
        Assert.assertEquals(transformerResponse.getIntelligence(),responseResponse.body().getIntelligence());
        Assert.assertEquals(transformerResponse.getSpeed(),responseResponse.body().getSpeed());
        Assert.assertEquals(transformerResponse.getSpeed(),responseResponse.body().getSpeed());
        Assert.assertEquals(transformerResponse.getEndurance(),responseResponse.body().getEndurance());
        Assert.assertEquals(transformerResponse.getRank(),responseResponse.body().getRank());
        Assert.assertEquals(transformerResponse.getCourage(),responseResponse.body().getCourage());
        Assert.assertEquals(transformerResponse.getFirepower(),responseResponse.body().getFirepower());
        Assert.assertEquals(transformerResponse.getSkill(),responseResponse.body().getSkill());

    }

    @Test
    public void testFailedEditTransformerRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockService = new MockFailedTransformerApiService(delegate);

        CreateTransformerBody createTransformerBody =
                new CreateTransformerBody("0", "test", "A",2,5,7,9,2,5,6,8);

        //Actual Test
        Call<TransformerResponse> responseCall = mockService.editTransformer("bear token", createTransformerBody);
        Response<TransformerResponse> responseResponse = responseCall.execute();
        Assert.assertFalse(responseResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(responseResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, responseResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }

    @Test
    public void testGetTransformersListRetrieval() throws Exception {
        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        TransformerApi mockApi = new MockTransformerApiService(delegate);

        TransformersList list = new TransformersList();
        ArrayList<TransformerResponse> arrayList = new ArrayList<>();

        arrayList.add(new TransformerResponse("0", "test", "A",2,5,7,9,2,5,6,8, "url"));
        list.setTransformers(arrayList);

        //Actual test
        Call<TransformersList> responseCall = mockApi.getTransformersList("bear token");
        Response<TransformersList> responseResponse = responseCall.execute();

        //Asserting response
        Assert.assertTrue(responseResponse.isSuccessful());
        Assert.assertEquals(list.getTransformers().get(0).getId(),responseResponse.body().getTransformers().get(0).getId());
        Assert.assertEquals(list.getTransformers().get(0).getName(),responseResponse.body().getTransformers().get(0).getName());
        Assert.assertEquals(list.getTransformers().get(0).getTeam(),responseResponse.body().getTransformers().get(0).getTeam());
        Assert.assertEquals(list.getTransformers().get(0).getStrength(),responseResponse.body().getTransformers().get(0).getStrength());
        Assert.assertEquals(list.getTransformers().get(0).getIntelligence(),responseResponse.body().getTransformers().get(0).getIntelligence());
        Assert.assertEquals(list.getTransformers().get(0).getSpeed(),responseResponse.body().getTransformers().get(0).getSpeed());
        Assert.assertEquals(list.getTransformers().get(0).getSpeed(),responseResponse.body().getTransformers().get(0).getSpeed());
        Assert.assertEquals(list.getTransformers().get(0).getEndurance(),responseResponse.body().getTransformers().get(0).getEndurance());
        Assert.assertEquals(list.getTransformers().get(0).getRank(),responseResponse.body().getTransformers().get(0).getRank());
        Assert.assertEquals(list.getTransformers().get(0).getCourage(),responseResponse.body().getTransformers().get(0).getCourage());
        Assert.assertEquals(list.getTransformers().get(0).getFirepower(),responseResponse.body().getTransformers().get(0).getFirepower());
        Assert.assertEquals(list.getTransformers().get(0).getSkill(),responseResponse.body().getTransformers().get(0).getSkill());
    }

    @Test
    public void testFailedGetTransformersListRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockService = new MockFailedTransformerApiService(delegate);

        //Actual Test
        Call<TransformersList> token = mockService.getTransformersList("Bear token");
        Response<TransformersList> tokenResponse = token.execute();
        Assert.assertFalse(tokenResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(tokenResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, tokenResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }

    @Test
    public void testDeleteTransformerRetrieval() throws Exception {
        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        TransformerApi mockApi = new MockTransformerApiService(delegate);

        //Actual test
        Call<Void> token = mockApi.deleteTransformer("Bear token", "1");
        Response<Void> tokenResponse = token.execute();

        //Asserting response
        Assert.assertTrue(tokenResponse.isSuccessful());
        Assert.assertEquals("","");

    }

    @Test
    public void testFailedDeleteTransformerRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockService = new MockFailedTransformerApiService(delegate);

        //Actual Test
        Call<Void> token = mockService.deleteTransformer("Bear token","1");
        Response<Void> tokenResponse = token.execute();
        Assert.assertFalse(tokenResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(tokenResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, tokenResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }
}
