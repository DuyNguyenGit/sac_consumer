package com.sacombank.consumers.models.qr;

import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.sacombank.consumers.api.ApiManager;
import com.stb.api.bo.consumer.ConsumerQRPayment;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.listeners.ApiResponse;

/**
 * Created by nguyenletruong on 9/17/17.
 */

public class PaymentByQRCodeInteractorImpl implements PaymentByQRCodeInteractor {
    @Override
    public void paymentByQRCode(PushPaymentData paymentData, CardObject cardObject,
                                String confirmPassword, final OnPaymentByQRCodeFinishedListener listener) {
        ConsumerQRPayment object = new ConsumerQRPayment();
        object.ConfirmPassword = confirmPassword;
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;
        object.BillNumber = paymentData.getAdditionalData().getBillNumber() != null ?
                paymentData.getAdditionalData().getBillNumber() : paymentData.getAdditionalData().getTerminalId();
        object.mVisaMID = paymentData.getMerchantIdentifierVisa02() != null ?
                paymentData.getMerchantIdentifierVisa02() : paymentData.getMerchantIdentifierVisa03();
        object.MasterPassQRID = paymentData.getMerchantIdentifierMastercard04() != null ?
                paymentData.getMerchantIdentifierMastercard04() : paymentData.getMerchantIdentifierMastercard05();
        object.Tips = paymentData.getTipOrConvenienceIndicator();
        object.MCC = paymentData.getMerchantCategoryCode();
        Double amount = paymentData.getTransactionAmount();
        if(amount != null) {
            object.Amount = amount.toString();
        }

        Double fee = paymentData.getValueOfConvenienceFeeFixed();
        if (fee != null) {
            object.Fee = fee.toString();
        }

        object.CurrencyCode = paymentData.getTransactionCurrencyCode();
        object.MerchantName = paymentData.getMerchantName();
        object.MerchantCity = paymentData.getMerchantCity();
        object.MerchantCountry = paymentData.getCountryCode();
        ApiManager.requestConsumerQRPayment(object, new ApiResponse<ConsumerQRPayment>() {
            @Override
            public void onSuccess(ConsumerQRPayment result) {
                listener.onSuccess(result);
            }

            @Override
            public void onError(ConsumerQRPayment error) {
                listener.onError(error);
            }
        });
    }
}
