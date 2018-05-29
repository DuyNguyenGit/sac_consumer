package com.sacombank.consumers.views.qr;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by nguyenletruong on 9/17/17.
 */

public interface PaymentByQRCodeView<T> extends BaseView {
    void onError(T error);
    void onSuccess(T data);
    void goToHomePage();
}
