package com.sacombank.consumers.presenter.loginregister.register;

import com.sacombank.consumers.models.signup.CreatePassModel;
import com.sacombank.consumers.models.signup.CreatePassModelImpl;
import com.sacombank.consumers.models.signup.CreatePassResultListener;
import com.sacombank.consumers.views.loginregister.register.CreatePassView;

/**
 * Created by DUY on 9/7/2017.
 */

public class CreatePassImpl implements CreatePassPresenter, CreatePassResultListener {

    CreatePassView createPassView;
    CreatePassModel createPassModel;

    public CreatePassImpl(CreatePassView createPassView) {
        this.createPassView = createPassView;
        this.createPassModel = new CreatePassModelImpl(this);
    }


    @Override
    public void createPass(String password) {
        createPassView.showLoading();
        createPassModel.createPassword(password);
    }


    @Override
    public void onCreatePassSuccess(Object data) {
        this.createPassView.hideLoading();
        this.createPassView.onSuccess(data);
    }

    @Override
    public void onCreatePassError(Object error) {
        this.createPassView.hideLoading();
        this.createPassView.onError(error);
    }
}
