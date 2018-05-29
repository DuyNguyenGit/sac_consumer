package com.sacombank.consumers.views.loginregister.register;

import com.sacombank.consumers.views.baseview.BaseView;

public interface SignUp1View<T> extends BaseView {

    void onError(T signInError);
    void onSuccess(T user);
}
