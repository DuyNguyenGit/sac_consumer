package com.sacombank.consumers.views.accountmanager;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 9/8/2017.
 */

public interface UpdatePassView<T> extends BaseView {

    void onError(T signInError);

    void onSuccess(T user);
}
