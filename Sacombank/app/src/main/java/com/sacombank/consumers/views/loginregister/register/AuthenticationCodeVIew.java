package com.sacombank.consumers.views.loginregister.register;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 9/7/2017.
 */

public interface AuthenticationCodeVIew<T> extends BaseView {


    void onError(T signInError);

    void onSuccess(T user);
}
