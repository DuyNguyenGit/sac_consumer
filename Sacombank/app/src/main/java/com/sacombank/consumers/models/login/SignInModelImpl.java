package com.sacombank.consumers.models.login;

import android.os.Handler;

import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.LoginData;
import com.sacombank.consumers.models.jsonobjects.User;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 7/15/2017.
 */

public class SignInModelImpl implements SignInModel {

    SignInResultListener signInResultListener;

    public SignInModelImpl(SignInResultListener signInResultListener) {
        this.signInResultListener = signInResultListener;
    }

    @Override
    public void signIn(LoginData loginData) {
        ConsumerAccountLogin object = new ConsumerAccountLogin();
        object.MobileNo = loginData.getMobileNo();
        object.Password = loginData.getPassword();
        object.FirebaseToken = loginData.getFirebaseToken();
        ApiManager.requestConsumerLogin(object, new ApiResponse<ConsumerAccountLogin>() {
            @Override
            public void onSuccess(ConsumerAccountLogin result) {
                signInResultListener.onSignInSuccess(result);
            }

            @Override
            public void onError(ConsumerAccountLogin error) {
                signInResultListener.onSignInError(error);
            }
        });
    }

    private void onResult() {
        User user = new User("Nguyen Van Duy", "male", User.MALE);
        this.signInResultListener.onSignInSuccess(user);
    }
}
