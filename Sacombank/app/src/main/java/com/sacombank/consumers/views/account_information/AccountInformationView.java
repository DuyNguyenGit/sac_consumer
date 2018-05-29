package com.sacombank.consumers.views.account_information;

import com.sacombank.consumers.views.baseview.BaseView;

/**
 * Created by TRANVIETTHUC on 16/09/2017.
 */

public interface AccountInformationView<T> extends BaseView {

    void onError(T signInError);

    void onSuccess(T user);
}
