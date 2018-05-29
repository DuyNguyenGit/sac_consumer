package com.sacombank.consumers.models.signup;

/**
 * Created by DUY on 7/15/2017.
 */

public interface SignUp1ResultListener<T> {

    void onVerifyCardSuccess(T data);

    void onVerifyCardError(T error);
}
