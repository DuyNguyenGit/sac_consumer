package com.sacombank.consumers.models.jsonobjects;

/**
 * Created by DUY on 9/7/2017.
 */

public class CardVerification {

    private String MobileNo;
    private String CardNumber;
    private String CVV;
    private String CardToken;
    private String ExpiryDate;

    public CardVerification(String mobileNo, String cardNumber, String CVV) {
        MobileNo = mobileNo;
        CardNumber = cardNumber;
        this.CVV = CVV;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getCardToken() {
        return CardToken;
    }

    public void setCardToken(String cardToken) {
        CardToken = cardToken;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }
}
