package com.sacombank.consumers.models.signup;


import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.CardVerification;
import com.stb.api.bo.consumer.ConsumerCardVerification;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by DUY on 7/15/2017.
 */

public class SignUp1ModelImpl implements SignUp1Model {

    SignUp1ResultListener signUp1ResultListener;

    public SignUp1ModelImpl(SignUp1ResultListener signUp1ResultListener) {
        this.signUp1ResultListener = signUp1ResultListener;
    }

    @Override
    public void verifyCard(CardVerification cardData) {
        ConsumerCardVerification object = new ConsumerCardVerification();
        object.MobileNo = cardData.getMobileNo();
        object.CardNumber = cardData.getCardNumber();
        object.CVV = cardData.getCVV();
        object.CardToken = cardData.getCardToken();
        object.ExpiryDate = cardData.getExpiryDate();
        ApiManager.requestConsumerCardVerification(object, new ApiResponse<ConsumerCardVerification>() {
            @Override
            public void onSuccess(ConsumerCardVerification result) {
                signUp1ResultListener.onVerifyCardSuccess(result);
            }

            @Override
            public void onError(ConsumerCardVerification error) {
                signUp1ResultListener.onVerifyCardError(error);
            }
        });
    }
}
