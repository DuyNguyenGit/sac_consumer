package com.sacombank.consumers.models.account_information;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.accountmanager.UpdatePassModelImpl;
import com.sacombank.consumers.models.accountmanager.UpdatePassResultListener;
import com.stb.api.bo.consumer.ConsumerAccountUpdate;
import com.stb.api.bo.consumer.L2.AccountObject;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by TRANVIETTHUC on 16/09/2017.
 */

public class Account_InformationModelImpl implements Account_InformationModel {
    private static final String TAG = UpdatePassModelImpl.class.getSimpleName();

    Account_InformationResultListenner resultListenner;

    public Account_InformationModelImpl(Account_InformationResultListenner updateInformationResultListener) {
        this.resultListenner = updateInformationResultListener;
    }

    @Override
    public void updateInformation(AccountObject accountObject) {
        ConsumerAccountUpdate object = new ConsumerAccountUpdate();
        object.AccountObject = accountObject;
        ApiManager.requestConsumerAccountUpdate(object, new ApiResponse<ConsumerAccountUpdate>() {
            @Override
            public void onSuccess(ConsumerAccountUpdate result) {
                Log.e(TAG, "onSuccess: UpdateInformation Account >>>" + new Gson().toJson(result));
                resultListenner.onUpdateAccountInformationSuccess(result);
            }

            @Override
            public void onError(ConsumerAccountUpdate error) {
                Log.e(TAG, "onError: UpdateInformation Account >>>" + new Gson().toJson(error));
                resultListenner.onUpdateAccountInformationError(error);
            }
        });
    }

}
