package com.sacombank.consumers.views.loginregister.login;

import com.sacombank.consumers.models.jsonobjects.SignInError;
import com.sacombank.consumers.models.jsonobjects.User;
import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by DUY on 7/15/2017.
 */

public interface SignInView<T> extends BaseView {
    void onError(T signInError);
    void onSuccess(T user);
}
