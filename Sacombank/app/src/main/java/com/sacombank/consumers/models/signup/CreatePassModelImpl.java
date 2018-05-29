package com.sacombank.consumers.models.signup;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.stb.api.bo.consumer.ConsumerAuthCodeVerification;
import com.stb.api.bo.consumer.ConsumerPasswordCreation;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/7/2017.
 */

public class CreatePassModelImpl implements CreatePassModel {

    private static final String TAG = CreatePassModelImpl.class.getSimpleName();
    CreatePassResultListener createPassResultListener;

    public CreatePassModelImpl(CreatePassResultListener createPassResultListener) {
        this.createPassResultListener = createPassResultListener;
    }

    @Override
    public void createPassword(String password) {
        ConsumerPasswordCreation object = new ConsumerPasswordCreation();
        object.Password = password;
        ApiManager.requestConsumerPasswordCreation(object, new ApiResponse<ConsumerPasswordCreation>() {
            @Override
            public void onSuccess(ConsumerPasswordCreation result) {
                Log.e(TAG, "onSuccess: ConsumerPasswordCreation >>>" + new Gson().toJson(result));
                createPassResultListener.onCreatePassSuccess(result);
            }

            @Override
            public void onError(ConsumerPasswordCreation error) {
                Log.e(TAG, "onError: ConsumerPasswordCreation >>>" + new Gson().toJson(error));
                createPassResultListener.onCreatePassError(error);
            }
        });
    }
}
