package com.sacombank.consumers.presenter.cardmanagement;

import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/15/2017.
 */

public interface BalancePresenter {
    void getBalance(CardObject cardObject);
}
