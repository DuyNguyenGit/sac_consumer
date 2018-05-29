package com.sacombank.consumers.models.signup;

import com.sacombank.consumers.models.jsonobjects.CardVerification;

public interface SignUp1Model {
    void verifyCard(CardVerification cardData);
}