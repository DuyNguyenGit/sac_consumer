package com.sacombank.consumers.models.signup;

/**
 * Created by DUY on 9/7/2017.
 */

public interface AuthenticationResultListener<T> {

    void onAuthenticationSuccess(T data);

    void onAuthenticationError(T error);
}
