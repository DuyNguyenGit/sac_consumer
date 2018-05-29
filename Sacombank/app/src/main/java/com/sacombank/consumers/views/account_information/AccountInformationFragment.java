package com.sacombank.consumers.views.account_information;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.presenter.account_information.AccountInformationPresenter;
import com.sacombank.consumers.presenter.account_information.AccountInformationPresenterImpl;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.widgets.DialogFactory;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.ConsumerAccountUpdate;
import com.stb.api.bo.consumer.ConsumerPasswordChange;
import com.stb.api.bo.consumer.L2.AccountObject;

/**
 * Created by Truong Thien on 12/08/2017.
 */

public class AccountInformationFragment extends BaseFragment implements AccountInformationView {
    private static final String TAG = AccountInformationFragment.class.getSimpleName();;
    private EditText email;
    private ImageView edit;
    private TextView tvUserName;
    private TextView tvMobileNo;
    private TextView tvAddress;
    private TextView tvCity;
    private TextView tvMarketing;
    private Button btnHoanTatAccountInfomation;
    private RadioButton radioNAM,radioNU;
    private AccountObject account=null;
    private AccountInformationPresenter accountPresenter;
    public AccountInformationFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(AccountInformationFragment.class.getSimpleName(), "Thông tin người dùng");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_information, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
        setValueAccount();
    }

    private void setValueAccount() {
        try{
            account= ApiManager.getAccountObject();
            if(account!=null){
                tvUserName.setText(account.FullName);
                tvMobileNo.setText(account.MobileNo);
                email.setText(account.Email);
                tvAddress.setText(account.Address);
                tvCity.setText(account.City);
                if(account.Gender.trim().equalsIgnoreCase("M")){
                    radioNAM.setChecked(true);
                    radioNU.setChecked(false);
                }else {
                    radioNAM.setChecked(false);
                    radioNU.setChecked(true);
                }
                tvMarketing.setText(account.SSN);
            }
        }catch (Exception e){
            Log.e("ERROR:",e.getMessage());
        }
    }

    private void addEvents(View view) {
        email.setEnabled(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setEnabled(true);
                email.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        btnHoanTatAccountInfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(account!=null){
                        account.FullName=tvUserName.getText().toString();
                        account.MobileNo=tvMobileNo.getText().toString();
                        account.Email=email.getText().toString();
                        account.Address=tvAddress.getText().toString();
                        account.City=tvCity.getText().toString();
                        if(radioNAM.isChecked())
                            account.Gender="M";
                        else
                            account.Gender="F";
                        account.SSN=tvMarketing.getText().toString();
                        accountPresenter.updateInformation(account);
                    }
                }catch (Exception e){
                    Log.e("ERROR:",e.getMessage());
                }
            }
        });
    }

    private void addControllers(View view) {
        email = (EditText) view.findViewById(R.id.edtEmail);
        edit = (ImageView) view.findViewById(R.id.imgEditMail);
        btnHoanTatAccountInfomation = (Button) view.findViewById(R.id.btnHoanTatAccountInfomation);
        tvUserName = (TextView) view.findViewById(R.id.edtTenAcount);
        tvMobileNo = (TextView) view.findViewById(R.id.edtSoDienThoai);
        tvAddress = (TextView) view.findViewById(R.id.edtDiaChi);
        tvCity = (TextView) view.findViewById(R.id.edtThanhPho);
        tvMarketing = (TextView) view.findViewById(R.id.edtMarketing);
        radioNAM= (RadioButton) view.findViewById(R.id.radioNAM);
        radioNU= (RadioButton) view.findViewById(R.id.radioNU);
        accountPresenter=new AccountInformationPresenterImpl(this);
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
    public void onError(Object signInError) {
        Log.e(TAG, "onError: >>>" + new Gson().toJson(signInError));
        if (signInError != null) {
            final String errorMsg = ((ConsumerPasswordChange) signInError).Description;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                    com.sacombank.consumers.views.widgets.dialog.DialogFactory.createMessageDialog(getActivity(), errorMsg);
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
    public void onSuccess(final Object user) {
        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(user));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(((ConsumerAccountUpdate)user).AccountObject));
                DialogFactory.createMessageDialogRegister(getContext(),getResources().getString(R.string.update_information_success));
            }
        });
    }

}
