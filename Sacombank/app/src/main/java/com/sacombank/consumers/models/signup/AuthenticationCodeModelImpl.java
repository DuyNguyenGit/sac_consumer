package com.sacombank.consumers.models.signup;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.stb.api.bo.consumer.ConsumerAuthCodeVerification;
import com.stb.api.bo.consumer.ConsumerCardVerification;
import com.stb.api.bo.consumer.ConsumerPasswordCreation;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/7/2017.
 */

public class AuthenticationCodeModelImpl implements AuthenticationCodeModel {


    private static final String TAG = AuthenticationCodeModelImpl.class.getSimpleName();
    AuthenticationResultListener authenticationResultListener;

    public AuthenticationCodeModelImpl(AuthenticationResultListener authenticationResultListener) {
        this.authenticationResultListener = authenticationResultListener;
    }

    @Override
    public void authenticate(String code) {

        ConsumerAuthCodeVerification object = new ConsumerAuthCodeVerification();
        object.AuthCode = code;
        ApiManager.requestConsumerAuthCodeVerification(object, new ApiResponse<ConsumerAuthCodeVerification>() {
            @Override
            public void onSuccess(ConsumerAuthCodeVerification result) {
                Log.e(TAG, "onSuccess: ConsumerAuthCodeVerification >>>" + new Gson().toJson(result));
                authenticationResultListener.onAuthenticationSuccess(result);
            }

            @Override
            public void onError(ConsumerAuthCodeVerification error) {
                Log.e(TAG, "onError: ConsumerAuthCodeVerification >>>" + new Gson().toJson(error));
                authenticationResultListener.onAuthenticationError(error);
            }
        });
    }
}
