package com.sacombank.consumers.models.jsonobjects;

/**
 * Created by DUY on 9/9/2017.
 */

public class HistoryData {
    private String cardToken;

    public HistoryData(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }
}
