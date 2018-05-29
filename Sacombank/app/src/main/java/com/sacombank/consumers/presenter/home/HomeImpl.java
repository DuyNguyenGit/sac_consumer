package com.sacombank.consumers.presenter.home;

import com.sacombank.consumers.models.home.HomeDataCallback;
import com.sacombank.consumers.models.home.HomeModel;
import com.sacombank.consumers.models.home.HomeModelImpl;
import com.sacombank.consumers.views.home.HomeView;

/**
 * Created by DUY on 7/15/2017.
 */

public class HomeImpl implements HomePresenter, HomeDataCallback {

    HomeView homeView;
    HomeModel homeModel;

    public HomeImpl(HomeView homeView) {
        this.homeView = homeView;
        homeModel = new HomeModelImpl(this);
    }



    @Override
    public void loadHomeData() {
        homeView.showLoading();
        homeModel.loadHomeData();
    }


    @Override
    public void onLoadHomeSuccess(Object user) {
        this.homeView.hideLoading();
        this.homeView.onSuccess(user);
    }

    @Override
    public void onLoadHomeError(Object error) {
        this.homeView.hideLoading();
        this.homeView.onSuccess(error);
    }
}
