package com.sacombank.consumers.views.home;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 9/2/2017.
 */

public interface HomeView<T> extends BaseView {

    void onError(T signInError);

    void onSuccess(T user);
}
