package com.sacombank.consumers.presenter.account_information;

import com.sacombank.consumers.models.account_information.Account_InformationModel;
import com.sacombank.consumers.models.account_information.Account_InformationModelImpl;
import com.sacombank.consumers.models.account_information.Account_InformationResultListenner;
import com.sacombank.consumers.views.account_information.AccountInformationView;
import com.stb.api.bo.consumer.L2.AccountObject;

/**
 * Created by TRANVIETTHUC on 16/09/2017.
 */

public class AccountInformationPresenterImpl implements AccountInformationPresenter,Account_InformationResultListenner {

    private AccountInformationView accountView;
    private Account_InformationModel model;

    public AccountInformationPresenterImpl(AccountInformationView view) {
        this.accountView=view;
        model=new Account_InformationModelImpl(this);
    }

    @Override
    public void updateInformation(AccountObject accountObject) {
        accountView.showLoading();
        model.updateInformation(accountObject);
    }

    @Override
    public void onUpdateAccountInformationSuccess(Object data) {
        this.accountView.hideLoading();
        this.accountView.onSuccess(data);
    }

    @Override
    public void onUpdateAccountInformationError(Object error) {
        this.accountView.hideLoading();
        this.accountView.onError(error);
    }
}
