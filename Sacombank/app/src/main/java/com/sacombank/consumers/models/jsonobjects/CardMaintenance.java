package com.sacombank.consumers.models.jsonobjects;

import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/16/2017.
 */

public class CardMaintenance {
    private CardObject cardObject;
    private String password;

    public CardMaintenance(CardObject cardObject, String password) {
        this.cardObject = cardObject;
        this.password = password;
    }

    public CardObject getCardObject() {
        return cardObject;
    }

    public void setCardObject(CardObject cardObject) {
        this.cardObject = cardObject;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
