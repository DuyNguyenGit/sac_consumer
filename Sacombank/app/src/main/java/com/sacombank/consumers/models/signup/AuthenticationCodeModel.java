package com.sacombank.consumers.models.signup;

/**
 * Created by DUY on 9/7/2017.
 */

public interface AuthenticationCodeModel {
    void authenticate(String code);
}
