package com.sacombank.consumers.models.login;

import com.sacombank.consumers.models.jsonobjects.SignInError;
import com.sacombank.consumers.models.jsonobjects.User;

/**
 * Created by DUY on 7/15/2017.
 */

public interface SignInResultListener<T> {

    void onSignInSuccess(T user);

    void onSignInError(T signInError);
}
