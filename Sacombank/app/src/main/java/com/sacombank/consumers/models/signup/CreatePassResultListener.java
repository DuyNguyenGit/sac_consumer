package com.sacombank.consumers.models.signup;

/**
 * Created by DUY on 9/7/2017.
 */

public interface CreatePassResultListener<T> {

    void onCreatePassSuccess(T data);

    void onCreatePassError(T error);
}
