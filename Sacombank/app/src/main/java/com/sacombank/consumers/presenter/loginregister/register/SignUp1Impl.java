package com.sacombank.consumers.presenter.loginregister.register;

import com.sacombank.consumers.models.jsonobjects.CardVerification;
import com.sacombank.consumers.models.signup.SignUp1Model;
import com.sacombank.consumers.models.signup.SignUp1ModelImpl;
import com.sacombank.consumers.models.signup.SignUp1ResultListener;
import com.sacombank.consumers.views.loginregister.register.SignUp1View;

/**
 * Created by DUY on 7/15/2017.
 */

public class SignUp1Impl implements SignUp1Presenter, SignUp1ResultListener {


    SignUp1View signUp1View;
    SignUp1Model signUp1Model;

    public SignUp1Impl(SignUp1View signUp1View) {
        this.signUp1View = signUp1View;
        signUp1Model = new SignUp1ModelImpl(this);
    }

    @Override
    public void verifyCard(CardVerification cardData) {
        signUp1View.showLoading();
        signUp1Model.verifyCard(cardData);
    }

    @Override
    public void onVerifyCardSuccess(Object data) {
        this.signUp1View.hideLoading();
        this.signUp1View.onSuccess(data);
    }

    @Override
    public void onVerifyCardError(Object error) {
        this.signUp1View.hideLoading();
        this.signUp1View.onError(error);
    }
}
