package com.sacombank.consumers.models.login;

import com.sacombank.consumers.models.jsonobjects.LoginData;

/**
 * Created by DUY on 7/15/2017.
 */

public interface SignInModel {
    void signIn(LoginData loginData);
}
