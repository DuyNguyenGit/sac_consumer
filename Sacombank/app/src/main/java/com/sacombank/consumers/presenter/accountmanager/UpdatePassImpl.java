package com.sacombank.consumers.presenter.accountmanager;

import com.sacombank.consumers.models.accountmanager.UpdatePassModel;
import com.sacombank.consumers.models.accountmanager.UpdatePassModelImpl;
import com.sacombank.consumers.models.accountmanager.UpdatePassResultListener;
import com.sacombank.consumers.models.home.HomeModel;
import com.sacombank.consumers.models.home.HomeModelImpl;
import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;
import com.sacombank.consumers.views.accountmanager.UpdatePassView;
import com.sacombank.consumers.views.home.HomeView;

/**
 * Created by DUY on 9/8/2017.
 */

public class UpdatePassImpl implements UpdatePassPresenter, UpdatePassResultListener {


    UpdatePassView updatePassView;
    UpdatePassModel updatePassModel;

    public UpdatePassImpl(UpdatePassView updatePassView) {
        this.updatePassView = updatePassView;
        updatePassModel = new UpdatePassModelImpl(this);
    }


    @Override
    public void updatePassword(PasswordUpdating passwordUpdating) {
        updatePassView.showLoading();
        updatePassModel.updatePass(passwordUpdating);
    }

    @Override
    public void onUpdatePassSuccess(Object data) {
        this.updatePassView.hideLoading();
        this.updatePassView.onSuccess(data);
    }

    @Override
    public void onUpdatePassError(Object error) {
        this.updatePassView.hideLoading();
        this.updatePassView.onError(error);
    }
}
