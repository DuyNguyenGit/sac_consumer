package com.sacombank.consumers.presenter.cardmanagement;

import com.sacombank.consumers.models.cardmanagement.CardUpdateModel;
import com.sacombank.consumers.models.cardmanagement.CardUpdateModelImpl;
import com.sacombank.consumers.models.cardmanagement.CardUpdateResultListener;
import com.sacombank.consumers.models.jsonobjects.CardMaintenance;
import com.sacombank.consumers.views.cardmanagement.view.CardUpdateView;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/16/2017.
 */

public class CardUpdateImpl implements CardUpdatePresenter, CardUpdateResultListener {

    private CardUpdateView view;
    private CardUpdateModel model;

    public CardUpdateImpl(CardUpdateView view) {
        this.view = view;
        this.model = new CardUpdateModelImpl(this);
    }

    @Override
    public void updateCard(CardObject cardObject) {
        this.view.showLoading();
        this.model.updateCard(cardObject);
    }

    @Override
    public void updateCardMaintenance(CardMaintenance cardMaintenance) {

        this.view.showLoading();
        this.model.updateCardMaintenance(cardMaintenance);
    }

    @Override
    public void onCardUpdateSuccess(Object data) {
        this.view.hideLoading();
        this.view.onSuccess(data);
    }

    @Override
    public void onCardUpdateError(Object error) {

        this.view.hideLoading();
        this.view.onError(error);
    }
}
