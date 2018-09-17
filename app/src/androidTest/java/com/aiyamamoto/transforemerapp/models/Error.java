package com.aiyamamoto.transforemerapp.models;

/**
 * Created by aiyamamoto on 2018-09-16.
 */

public class Error {

    int code;
    String message;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
