package com.sacombank.consumers.models.cardmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.stb.api.bo.consumer.ConsumerCardBalanceInquiry;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/15/2017.
 */

public class BalanceModelImpl implements BalanceModel {

    private static final String TAG = BalanceModelImpl.class.getSimpleName();
    BalanceResultListener listener;

    public BalanceModelImpl(BalanceResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void getBalance(CardObject cardObject) {
        ConsumerCardBalanceInquiry object = new ConsumerCardBalanceInquiry();
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;

        ApiManager.requestConsumerCardBalanceInquiry(object, new ApiResponse<ConsumerCardBalanceInquiry>() {
            @Override
            public void onSuccess(ConsumerCardBalanceInquiry result) {
                Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(result));
                listener.onGetBalanceSuccess(result);
            }

            @Override
            public void onError(ConsumerCardBalanceInquiry error) {
                Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
                listener.onGetBalanceError(error);
            }
        });
    }
}
