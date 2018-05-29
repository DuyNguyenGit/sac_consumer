package com.sacombank.consumers.views.loginregister.register;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.models.jsonobjects.CardVerification;
import com.sacombank.consumers.presenter.loginregister.register.SignUp1Impl;
import com.sacombank.consumers.presenter.loginregister.register.SignUp1Presenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.loginregister.LoginListener;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.ConsumerCardVerification;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp1Fragment extends BaseFragment implements SignUp1View, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = SignUp1Fragment.class.getSimpleName();
    private TextView txtDKDK;
    private EditTextViewPlus txtEditPhone;
    private EditTextViewPlus txtEditCardNo;
    private EditTextViewPlus txtEditCVV;
    private Button btnFinish;
    private LoginListener listener;
    private SignUpListener signUpListener;
    private SignUp1Presenter signUp1Presenter;
    private CheckBox checkBox;

    public void setSignUpListener(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    @Override
    public void showLoading() {
        showLoadingPage();
    }

    @Override
    public void hideLoading() {
        hideLoadingPage();
    }

    @Override
    public void onError(Object error) {
        Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
        if (error != null) {
            final String errorMsg = ((ConsumerCardVerification) error).Description;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                    DialogFactory.createMessageDialog(getActivity(), errorMsg);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                }
            });
        }
    }

    @Override
    public void onSuccess(Object data) {
        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(data));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingPage();
                signUpListener.removeFragment();
                listener.nextSignUpConfirmInputPage();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setEnableButton(isChecked);
    }


    interface SignUpListener {
        void removeFragment();
        void addFragment();
    }

    public SignUp1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp1Presenter = new SignUp1Impl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtDKDK = (TextView) view.findViewById(R.id.txtDKDK);
        txtDKDK.setText(Html.fromHtml(getResources().getString(R.string.policy_mesage1) + ": <font color='white'><u>" + getResources().getString(R.string.policy_mesage2) + " </u></font>" + getResources().getString(R.string.policy_mesage3)));
        txtDKDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.denTrangDieuKhoan();
            }
        });
        btnFinish = (Button) view.findViewById(R.id.btnRegister);
        txtEditPhone = (EditTextViewPlus) view.findViewById(R.id.edtPhone);
        txtEditCardNo = (EditTextViewPlus) view.findViewById(R.id.edtCardNo);
        txtEditCVV = (EditTextViewPlus) view.findViewById(R.id.edtCVV);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox2);
        checkBox.setOnCheckedChangeListener(this);
        setEnableButton(checkBox.isChecked());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = txtEditPhone.getText().toString();
                String cardNo = txtEditCardNo.getText().toString();
                String CVV = txtEditCVV.getText().toString();
                CardVerification cardData = new CardVerification(mobileNo, cardNo, CVV);
                cardData.setCardToken("");
                cardData.setExpiryDate("");
                signUp1Presenter.verifyCard(cardData);
            }
        });

    }

    private void setEnableButton(boolean isChecked) {
        btnFinish.setEnabled(isChecked);
        if (!btnFinish.isEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btnFinish.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
            } else {
                btnFinish.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_disable));

            }
            btnFinish.setTextColor(getResources().getColor(R.color.disable_button_text_color));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btnFinish.setBackground(getResources().getDrawable(R.drawable.login_button_bg));
            } else {
                btnFinish.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_button_bg));

            }
            btnFinish.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

}
