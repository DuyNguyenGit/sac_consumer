package com.sacombank.consumers.presenter.loginregister.login;

import com.sacombank.consumers.models.jsonobjects.LoginData;
import com.sacombank.consumers.models.jsonobjects.SignInError;
import com.sacombank.consumers.models.jsonobjects.User;
import com.sacombank.consumers.models.login.SignInModel;
import com.sacombank.consumers.models.login.SignInModelImpl;
import com.sacombank.consumers.models.login.SignInResultListener;
import com.sacombank.consumers.views.loginregister.login.SignInView;

/**
 * Created by DUY on 7/15/2017.
 */

public class SignInImpl implements SignInPresenter, SignInResultListener {

    SignInView signInView;
    SignInModel signInModel;

    public SignInImpl(SignInView signInView) {
        this.signInView = signInView;
        signInModel = new SignInModelImpl(this);
    }


    @Override
    public void login(LoginData loginData) {
        signInView.showLoading();
        signInModel.signIn(loginData);
    }

    @Override
    public void onSignInSuccess(Object user) {
        this.signInView.hideLoading();
        this.signInView.onSuccess(user);
    }

    @Override
    public void onSignInError(Object signInError) {
        this.signInView.hideLoading();
        this.signInView.onError(signInError);
    }
}
