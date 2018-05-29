package com.sacombank.consumers.models.home;

/**
 * Created by DUY on 9/2/2017.
 */

public interface HomeDataCallback<T> {

    void onLoadHomeSuccess(T user);

    void onLoadHomeError(T signInError);
}
