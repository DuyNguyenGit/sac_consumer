package com.sacombank.consumers.models.sidemenu;

import android.content.Context;

import com.sacombank.consumers.R;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.utils.constant.Constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DUY on 7/16/2017.
 */

public class SideMenuDataFactory {

    private static final String TAG = SideMenuDataFactory.class.getSimpleName();
    private boolean isLogined;
    private Context context;
    private String home;
    private String introduce;
    private String account;
    private String account_register;
    private String account_update_pass;
    private String account_user;
    private String account_card_manager;
    private String payment;
    private String payment_qr;
    private String payment_bill;
    private String payment_credit;
    private String payment_card;
    private String online;
    private String policy;
    private String history;
    private String history_qr;
    private String history_pos;
    private String history_bill;
    private String history_credit;
    private String history_phone_card;
    private String language;

    public SideMenuDataFactory(Context context, boolean isLogined) {
        this.context = context;
        this.isLogined = isLogined;
        home = context.getString(R.string.home);
        introduce = context.getString(R.string.introduce);
        account = context.getString(R.string.account);
        account_register = isLogined ? context.getString(R.string.account_logout) : context.getString(R.string.account_register);
        account_update_pass = context.getString(R.string.account_update_pass);
        account_user = context.getString(R.string.account_user);
        account_card_manager = context.getString(R.string.account_card_manager);
        payment = context.getString(R.string.payment);
        payment_qr = context.getString(R.string.payment_qr);
        payment_bill = context.getString(R.string.payment_bill);
        payment_credit = context.getString(R.string.payment_credit);
        payment_card = context.getString(R.string.payment_card);
        online = context.getString(R.string.online);
        policy = context.getString(R.string.policy);
        history = context.getString(R.string.history);
        history_qr = context.getString(R.string.history_qr);
        history_pos = context.getString(R.string.history_pos);
        history_bill = context.getString(R.string.history_bill);
        history_credit = context.getString(R.string.history_credit);
        history_phone_card = context.getString(R.string.history_phone_card);
        language = context.getString(R.string.language);
    }



    public List<Section> makeSection() {
        return Arrays.asList(makeHomeSection(),
                makeAccountSection(),
                makePaymentSection(),
                makeHistorySection(),
                makeIntroduceSection()
                //makePolicySection()
                //makeLanguageSection()
        );
    }

    private Section makePolicySection() {
        return new Section(Constant.MENU_POLICY, policy, null, R.drawable.icon_dieukhoan, true);
    }

    private Section makeHistorySection() {
        if(userIsLogined()){
            return new Section(Constant.MENU_HISTORY, history, makeHistoryItems(), R.drawable.icon_lichsu, isLogined);
        }else {
            return new Section(Constant.MENU_HISTORY, history, makeHistoryItems(), R.drawable.icon_lichsu_notlogin, isLogined);
        }
    }

    private Section makeLanguageSection() {
        return new Section(language, null, R.drawable.ic_language);
    }


    public Section makeHomeSection() {
        return new Section(Constant.MENU_HOME, home, makeHomeItems(), R.drawable.icon_trangchu, true);
    }
    public List<SectionItem> makeHomeItems() {
        return null;
    }

    public Section makeIntroduceSection() {
        return new Section(Constant.MENU_ABOUT, introduce, makeIntroItems(), R.drawable.ic_menu_about, true);
    }
    public List<SectionItem> makeIntroItems() {
        return null;
    }

    public Section makeAccountSection() {
        return new Section(Constant.MENU_ACCOUNT, account, makeAccountItems(), R.drawable.icon_taikhoan, true);
    }

    public Section makePaymentSection() {
        if(userIsLogined()){
            return new Section(Constant.MENU_PAYMENT, payment, makePaymentItems(), R.drawable.icon_thanhtoan, true);
        }else {
            return new Section(Constant.MENU_PAYMENT, payment, makePaymentItems(), R.drawable.icon_thanhtoan_notlogin, true);
        }
    }

    protected boolean userIsLogined() {
        return ApiManager.getAccountObject() != null;
    }


    public List<SectionItem> makeAccountItems() {
        SectionItem accRegister = new SectionItem(account_register, true);
        SectionItem accUpdatePass = new SectionItem(account_update_pass, isLogined);
        SectionItem accUser = new SectionItem(account_user, isLogined);
        SectionItem accCardManager = new SectionItem(account_card_manager, isLogined);

        return Arrays.asList(accRegister, accUpdatePass, accUser, accCardManager);
    }


    public List<SectionItem> makePaymentItems() {
        SectionItem paymentQR = new SectionItem(payment_qr, isLogined);
        SectionItem paymentBill = new SectionItem(payment_bill, isLogined);
        SectionItem paymentCredit = new SectionItem(payment_credit, isLogined);
        SectionItem paymentCard = new SectionItem(payment_card, isLogined);
        return Arrays.asList(paymentQR);
//        return Arrays.asList(paymentQR, paymentBill, paymentCredit, paymentCard);
    }

//    public Section makeOnlineSection() {
//        return new Section(online, null, R.drawable.ic_online);
//    }


    private List<SectionItem> makeHistoryItems() {
//        SectionItem qr = new SectionItem(history_qr);
//        SectionItem pos = new SectionItem(history_pos);
//        SectionItem bill = new SectionItem(history_bill);
//        SectionItem credit = new SectionItem(history_credit);
//        SectionItem card = new SectionItem(history_phone_card);
//
//        return Arrays.asList(qr, pos, bill, credit, card);
        return null;
    }



}
