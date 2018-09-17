package com.aiyamamoto.transforemerapp;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.aiyamamoto.transforemerapp.network.TransformerApi;

import junit.framework.Assert;

import java.lang.annotation.Annotation;

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
 * To test Retrofit2 mocking HTTP responses
 * {@link TransformerApi#getAllsparkToken(String)}
 *
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

        //Actual test
        Call<String> token = mockApi.getAllsparkToken("");
        Response<String> tokenResponse = token.execute();

        //Asserting response
        Assert.assertTrue(tokenResponse.isSuccessful());
        Assert.assertEquals("faketoken", tokenResponse.body());

    }

    @SmallTest
    public void testFailedTokenRetrieval() throws Exception {

        BehaviorDelegate<TransformerApi> delegate = mockRetrofit.create(TransformerApi.class);
        MockFailedTransformerApiService mockQodService = new MockFailedTransformerApiService(delegate);

        //Actual Test
        Call<String> token = mockQodService.getAllsparkToken("");
        Response<String> tokenResponse = token.execute();
        Assert.assertFalse(tokenResponse.isSuccessful());

        Converter<ResponseBody, TransformerApiErrorResponse> errorConverter = retrofit.responseBodyConverter(TransformerApiErrorResponse.class, new Annotation[0]);
        TransformerApiErrorResponse error = errorConverter.convert(tokenResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, tokenResponse.code());
        Assert.assertEquals("Request Not Found", error.getError().getMessage());

    }
}
