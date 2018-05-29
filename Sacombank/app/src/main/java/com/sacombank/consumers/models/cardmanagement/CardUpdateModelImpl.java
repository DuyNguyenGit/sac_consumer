package com.sacombank.consumers.models.cardmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.CardMaintenance;
import com.stb.api.bo.consumer.ConsumerCardMaintenance;
import com.stb.api.bo.consumer.ConsumerCardUpdate;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 9/16/2017.
 */

public class CardUpdateModelImpl implements CardUpdateModel {

    private static final String TAG = CardUpdateModelImpl.class.getSimpleName();
    CardUpdateResultListener listener;

    public CardUpdateModelImpl(CardUpdateResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateCard(CardObject cardObject) {
        ConsumerCardUpdate object = new ConsumerCardUpdate();
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;
        object.DefaultIndicator = cardObject.DefaultIndicator;//turn on/off "Thẻ mặc định" trong màn hình thông tin chung của Quản Lý Thẻ
        ApiManager.requestConsumerCardUpdate(object, new ApiResponse<ConsumerCardUpdate>() {
            @Override
            public void onSuccess(ConsumerCardUpdate result) {
                Log.e(TAG, "onSuccess: ConsumerCardUpdate>>>" + new Gson().toJson(result));
                listener.onCardUpdateSuccess(result);
            }

            @Override
            public void onError(ConsumerCardUpdate error) {
                Log.e(TAG, "onError: ConsumerCardUpdate>>>" + new Gson().toJson(error));
                listener.onCardUpdateError(error);

            }
        });
    }

    @Override
    public void updateCardMaintenance(CardMaintenance cardMaintenance) {
        ConsumerCardMaintenance object = new ConsumerCardMaintenance();
        object.CardStatus = cardMaintenance.getCardObject().CardStatus;//turn on/off "Thẻ hoạt động" trong màn hình chung cùa Quản Lý
        object.CardNumber = cardMaintenance.getCardObject().CardNumber;
        object.CardToken = cardMaintenance.getCardObject().CardToken;
        object.Password = cardMaintenance.getPassword();
        ApiManager.requestConsumerCardMaintenance(object, new ApiResponse<ConsumerCardMaintenance>() {
            @Override
            public void onSuccess(ConsumerCardMaintenance result) {
                Log.e(TAG, "onSuccess: ConsumerCardMaintenance>>>" + new Gson().toJson(result));
                listener.onCardUpdateSuccess(result);
            }

            @Override
            public void onError(ConsumerCardMaintenance error) {

                Log.e(TAG, "onError: ConsumerCardMaintenance>>>" + new Gson().toJson(error));
                listener.onCardUpdateError(error);
            }
        });
    }
}
