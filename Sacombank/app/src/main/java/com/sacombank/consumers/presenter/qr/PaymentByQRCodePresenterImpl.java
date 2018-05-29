package com.sacombank.consumers.presenter.qr;

import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.sacombank.consumers.models.qr.PaymentByQRCodeInteractor;
import com.sacombank.consumers.models.qr.PaymentByQRCodeInteractorImpl;
import com.sacombank.consumers.views.qr.PaymentByQRCodeView;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by nguyenletruong on 9/17/17.
 */

public class PaymentByQRCodePresenterImpl implements PaymentByQRCodePresenter, PaymentByQRCodeInteractor.OnPaymentByQRCodeFinishedListener {
    private PaymentByQRCodeView paymentByQRCodeView;
    private PaymentByQRCodeInteractor paymentByQRCodeInteractor;

    public PaymentByQRCodePresenterImpl(PaymentByQRCodeView paymentByQRCodeView) {
        this.paymentByQRCodeView = paymentByQRCodeView;
        this.paymentByQRCodeInteractor = new PaymentByQRCodeInteractorImpl();
    }
    @Override
    public void validatePaymentByQRCode(PushPaymentData pushPaymentData, CardObject cardObject, String confirmPassword) {
        if (paymentByQRCodeView != null) {
            paymentByQRCodeView.showLoading();
        }

        paymentByQRCodeInteractor.paymentByQRCode(pushPaymentData, cardObject, confirmPassword, this);
    }

    @Override
    public void onDestroy() {
        paymentByQRCodeView = null;
    }

    @Override
    public void onSuccess(Object data) {
        if (paymentByQRCodeView != null){
            paymentByQRCodeView.hideLoading();
            paymentByQRCodeView.onSuccess(data);
        }
    }

    @Override
    public void onError(Object error) {
        if (paymentByQRCodeView != null) {
            paymentByQRCodeView.hideLoading();
            paymentByQRCodeView.onError(error);
        }
    }
}
