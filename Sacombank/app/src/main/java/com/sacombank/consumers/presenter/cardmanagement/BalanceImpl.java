package com.sacombank.consumers.presenter.cardmanagement;

import com.sacombank.consumers.models.cardmanagement.BalanceModel;
import com.sacombank.consumers.models.cardmanagement.BalanceModelImpl;
import com.sacombank.consumers.models.cardmanagement.BalanceResultListener;
import com.sacombank.consumers.views.cardmanagement.BalanceFragment;
import com.sacombank.consumers.views.cardmanagement.view.BalanceView;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/15/2017.
 */

public class BalanceImpl implements BalancePresenter, BalanceResultListener {

    BalanceView balanceView;
    BalanceModel balanceModel;

    public BalanceImpl(BalanceView balanceView) {
        this.balanceView = balanceView;
        this.balanceModel = new BalanceModelImpl(this);
    }

    @Override
    public void getBalance(CardObject cardObject) {
        this.balanceView.showLoading();
        this.balanceModel.getBalance(cardObject);
    }

    @Override
    public void onGetBalanceSuccess(Object data) {
        this.balanceView.hideLoading();
        this.balanceView.onSuccess(data);
    }

    @Override
    public void onGetBalanceError(Object error) {

        this.balanceView.hideLoading();
        this.balanceView.onError(error);
    }
}
