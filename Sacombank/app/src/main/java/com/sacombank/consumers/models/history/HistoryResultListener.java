package com.sacombank.consumers.models.history;

/**
 * Created by DUY on 9/9/2017.
 */

public interface HistoryResultListener<T> {

    void onLoadHistorySuccess(T data);

    void onLoadHistoryError(T error);
}
