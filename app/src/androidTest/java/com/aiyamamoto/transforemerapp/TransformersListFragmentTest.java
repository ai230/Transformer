package com.aiyamamoto.transforemerapp;

import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by aiyamamoto on 2018-09-16.
 */
public class TransformersListFragmentTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void setOrderTransformerListTest() throws Exception {

        TransformersListFragment fragment = mock(TransformersListFragment.class);

        // parameter
        TransformersList transformersList = new TransformersList();

        ArrayList<TransformerResponse> arrayList = new ArrayList<>();
        ArrayList<Transformer> expectedArrayList = new ArrayList<>();

        TransformerResponse tr1 = new TransformerResponse("0", "TF1", "A", 6,6,7,9,5,2,9,7, "");
        TransformerResponse tr2 = new TransformerResponse("0", "TF2", "A", 4,4,4,4,4,4,4,4, "");
        TransformerResponse tr3 = new TransformerResponse("0", "TF3", "D", 8,9,2,6,5,5,6,10, "");
        TransformerResponse tr4 = new TransformerResponse("0", "TF4", "A", 10, 5,1,8,3,9,9,8,"");
        TransformerResponse tr5 = new TransformerResponse("0", "TF5", "D", 7,9,5,8,7,6,8,8,"");

        Transformer tf1 = new Transformer("0","TF1",6,6,7,9,5,2,9,7, "2","");
        Transformer tf2 = new Transformer("0", "TF2", 4,4,4,4,4,4,4,4, "A","");
        Transformer tf3 = new Transformer("0", "TF3", 8,9,2,6,5,5,6,10, "D","");
        Transformer tf4 = new Transformer("0", "TF4", 10, 5,1,8,3,9,9,8,"A","");
        Transformer tf5 = new Transformer("0", "TF5", 7,9,5,8,7,6,8,8,"D","");

        arrayList.add(tr1);
        arrayList.add(tr2);
        arrayList.add(tr3);
        arrayList.add(tr4);
        arrayList.add(tr5);

        expectedArrayList.add(tf2);
        expectedArrayList.add(tf1);
        expectedArrayList.add(tf4);
        expectedArrayList.add(tf5);
        expectedArrayList.add(tf3);

        transformersList.setTransformers(arrayList); // param

        when(fragment.setOrderTransformerList(transformersList)).thenReturn(expectedArrayList);


        assertEquals(fragment.setOrderTransformerList(transformersList), expectedArrayList);


    }

    @After
    public void tearDown() throws Exception {
    }

}