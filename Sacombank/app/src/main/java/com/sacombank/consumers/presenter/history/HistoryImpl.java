package com.sacombank.consumers.presenter.history;

import com.sacombank.consumers.models.history.HistoryModel;
import com.sacombank.consumers.models.history.HistoryModelImpl;
import com.sacombank.consumers.models.history.HistoryResultListener;
import com.sacombank.consumers.models.home.HomeModel;
import com.sacombank.consumers.models.home.HomeModelImpl;
import com.sacombank.consumers.models.jsonobjects.HistoryData;
import com.sacombank.consumers.views.history.HistoryView;
import com.sacombank.consumers.views.home.HomeView;

/**
 * Created by DUY on 9/9/2017.
 */

public class HistoryImpl implements HistoryPresenter, HistoryResultListener {
    HistoryView historyView;
    HistoryModel historyModel;

    public HistoryImpl(HistoryView historyView) {
        this.historyView = historyView;
        historyModel = new HistoryModelImpl(this);
    }


    @Override
    public void loadHistoryData(HistoryData historyData) {
        historyView.showLoading();
        historyModel.loadHistoryData(historyData);
    }

    @Override
    public void onLoadHistorySuccess(Object data) {
        this.historyView.hideLoading();
        this.historyView.onSuccess(data);
    }

    @Override
    public void onLoadHistoryError(Object error) {
        this.historyView.hideLoading();
        this.historyView.onError(error);
    }
}
