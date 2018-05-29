package com.sacombank.consumers.models.cardmanagement;

/**
 * Created by DUY on 9/16/2017.
 */

public interface CardUpdateResultListener<T> {

    void onCardUpdateSuccess(T data);

    void onCardUpdateError(T error);
}
