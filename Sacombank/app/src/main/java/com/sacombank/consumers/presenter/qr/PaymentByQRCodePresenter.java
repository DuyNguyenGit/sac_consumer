package com.sacombank.consumers.presenter.qr;

import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by nguyenletruong on 9/17/17.
 */

public interface PaymentByQRCodePresenter {
    void validatePaymentByQRCode(PushPaymentData pushPaymentData, CardObject cardObject, String confirmPassword);
    void onDestroy();
}
