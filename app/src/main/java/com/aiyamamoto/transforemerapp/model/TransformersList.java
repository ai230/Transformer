package com.aiyamamoto.transforemerapp.model;

import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformersList {
    @SerializedName("transformers")
    private ArrayList<TransformerResponse> transformers;

    public ArrayList<TransformerResponse> getTransformers() {
        return transformers;
    }
}
