package com.sacombank.consumers.presenter.loginregister.register;

import com.sacombank.consumers.models.signup.AuthenticationCodeModel;
import com.sacombank.consumers.models.signup.AuthenticationCodeModelImpl;
import com.sacombank.consumers.models.signup.AuthenticationResultListener;
import com.sacombank.consumers.models.signup.SignUp1Model;
import com.sacombank.consumers.models.signup.SignUp1ModelImpl;
import com.sacombank.consumers.views.loginregister.register.AuthenticationCodeVIew;
import com.sacombank.consumers.views.loginregister.register.SignUp1View;

/**
 * Created by DUY on 9/7/2017.
 */

public class AuthenticationCodeImpl implements AuthenticationCodePresenter, AuthenticationResultListener {



    AuthenticationCodeVIew authenticationCodeVIew;
    AuthenticationCodeModel authenticationCodeModel;

    public AuthenticationCodeImpl(AuthenticationCodeVIew authenticationCodeVIew) {
        this.authenticationCodeVIew = authenticationCodeVIew;
        authenticationCodeModel = new AuthenticationCodeModelImpl(this);
    }

    @Override
    public void authenticate(String code) {
        authenticationCodeVIew.showLoading();
        authenticationCodeModel.authenticate(code);
    }


    @Override
    public void onAuthenticationSuccess(Object data) {
        this.authenticationCodeVIew.hideLoading();
        this.authenticationCodeVIew.onSuccess(data);
    }

    @Override
    public void onAuthenticationError(Object error) {
        this.authenticationCodeVIew.hideLoading();
        this.authenticationCodeVIew.onError(error);
    }
}
