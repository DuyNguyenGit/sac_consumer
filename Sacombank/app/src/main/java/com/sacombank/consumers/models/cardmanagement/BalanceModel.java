package com.sacombank.consumers.models.cardmanagement;

import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/15/2017.
 */

public interface BalanceModel {
    void getBalance(CardObject cardObject);
}
