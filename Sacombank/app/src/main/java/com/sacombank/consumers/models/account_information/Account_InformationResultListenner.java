package com.sacombank.consumers.models.account_information;

/**
 * Created by TRANVIETTHUC on 16/09/2017.
 */

public interface Account_InformationResultListenner<T> {
        void onUpdateAccountInformationSuccess(T data);
        void onUpdateAccountInformationError(T error);
}