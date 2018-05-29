package com.sacombank.consumers.models.cardmanagement;

/**
 * Created by DUY on 9/15/2017.
 */

public interface BalanceResultListener<T> {

    void onGetBalanceSuccess(T data);

    void onGetBalanceError(T error);
}
