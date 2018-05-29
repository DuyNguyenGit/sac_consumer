package com.sacombank.consumers.models.history;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.accountmanager.UpdatePassModelImpl;
import com.sacombank.consumers.models.accountmanager.UpdatePassResultListener;
import com.sacombank.consumers.models.jsonobjects.HistoryData;
import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;
import com.stb.api.bo.consumer.ConsumerCardMiniStatement;
import com.stb.api.bo.consumer.ConsumerPasswordChange;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/9/2017.
 */

public class HistoryModelImpl implements HistoryModel {

    private static final String TAG = HistoryModelImpl.class.getSimpleName();
    HistoryResultListener historyResultListener;

    public HistoryModelImpl(HistoryResultListener historyResultListener) {
        this.historyResultListener = historyResultListener;
    }


    @Override
    public void loadHistoryData(HistoryData historyData) {
        ConsumerCardMiniStatement object = new ConsumerCardMiniStatement();
        object.CardToken = historyData.getCardToken();
        ApiManager.requestConsumerCardMiniStatement(object, new ApiResponse<ConsumerCardMiniStatement>() {
            @Override
            public void onSuccess(ConsumerCardMiniStatement result) {
                Log.e(TAG, "onSuccess: ConsumerCardMiniStatement >>>" + new Gson().toJson(result));
                historyResultListener.onLoadHistorySuccess(result);
            }

            @Override
            public void onError(ConsumerCardMiniStatement error) {
                Log.e(TAG, "onError: ConsumerCardMiniStatement >>>" + new Gson().toJson(error));
                historyResultListener.onLoadHistoryError(error);
            }
        });
    }
}
