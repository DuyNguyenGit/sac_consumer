package com.sacombank.consumers.models.qr;

import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by nguyenletruong on 9/17/17.
 */

public interface PaymentByQRCodeInteractor {
    interface OnPaymentByQRCodeFinishedListener<T> {
        void onSuccess(T data);
        void onError(T error);
    }

    void paymentByQRCode(PushPaymentData paymentData, CardObject cardObject, String confirmPassword, OnPaymentByQRCodeFinishedListener listener);
}
