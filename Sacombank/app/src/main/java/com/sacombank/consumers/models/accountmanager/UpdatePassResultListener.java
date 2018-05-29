package com.sacombank.consumers.models.accountmanager;

/**
 * Created by DUY on 9/8/2017.
 */

public interface UpdatePassResultListener<T> {


    void onUpdatePassSuccess(T data);

    void onUpdatePassError(T error);
}
