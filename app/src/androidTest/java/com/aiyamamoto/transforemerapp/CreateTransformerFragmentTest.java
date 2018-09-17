package com.aiyamamoto.transforemerapp;

import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This is Unit Test for {@link CreateTransformerFragment#createTransformerBody()} .
 * Use Mockito framework (mocking framework)
 *
 * Created by aiyamamoto on 2018-09-16.
 */
public class CreateTransformerFragmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createTransformerBodyTest() throws Exception {

        CreateTransformerFragment fragment = mock(CreateTransformerFragment.class);

        CreateTransformerBody body =
                new CreateTransformerBody("0", "test", "A",2,5,7,9,2,5,6,8);

        when(fragment.createTransformerBody()).thenReturn(body);

        assertEquals(fragment.createTransformerBody(), body);
    }


    @After
    public void tearDown() throws Exception {
    }

}