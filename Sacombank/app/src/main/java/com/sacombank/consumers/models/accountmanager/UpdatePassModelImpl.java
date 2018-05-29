package com.sacombank.consumers.models.accountmanager;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.CardVerification;
import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;
import com.sacombank.consumers.models.signup.SignUp1ResultListener;
import com.stb.api.bo.consumer.ConsumerCardVerification;
import com.stb.api.bo.consumer.ConsumerPasswordChange;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/8/2017.
 */

public class UpdatePassModelImpl implements UpdatePassModel {

    private static final String TAG = UpdatePassModelImpl.class.getSimpleName();
    UpdatePassResultListener updatePassResultListener;

    public UpdatePassModelImpl(UpdatePassResultListener updatePassResultListener) {
        this.updatePassResultListener = updatePassResultListener;
    }


    @Override
    public void updatePass(PasswordUpdating passwordUpdating) {
        ConsumerPasswordChange object = new ConsumerPasswordChange();
        object.OldPassword = passwordUpdating.getOldPass();
        object.NewPassword = passwordUpdating.getNewPass();

        ApiManager.requestConsumerPasswordChange(object, new ApiResponse<ConsumerPasswordChange>() {
            @Override
            public void onSuccess(ConsumerPasswordChange result) {
                Log.e(TAG, "onSuccess: ConsumerPasswordChange >>>" + new Gson().toJson(result));
                updatePassResultListener.onUpdatePassSuccess(result);
            }

            @Override
            public void onError(ConsumerPasswordChange error) {

                Log.e(TAG, "onError: ConsumerPasswordChange >>>" + new Gson().toJson(error));
                updatePassResultListener.onUpdatePassError(error);
            }
        });
    }
}
