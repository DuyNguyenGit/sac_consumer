package com.sacombank.consumers.views.cardmanagement.view;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 9/16/2017.
 */

public interface CardUpdateView<T> extends BaseView {

    void onError(T error);

    void onSuccess(T data);
}
