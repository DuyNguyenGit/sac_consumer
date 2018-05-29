package com.sacombank.consumers.views.history;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 9/9/2017.
 */

public interface HistoryView<T> extends BaseView {

    void onError(T error);

    void onSuccess(T data);
}
