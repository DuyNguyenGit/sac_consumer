package com.sacombank.consumers.views.loginregister.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.presenter.loginregister.register.CreatePassImpl;
import com.sacombank.consumers.presenter.loginregister.register.CreatePassPresenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.loginregister.LoginListener;
import com.sacombank.consumers.views.widgets.ButtonPlus;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerAuthCodeVerification;
import com.stb.api.bo.consumer.ConsumerPasswordCreation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp2Fragment extends BaseFragment implements View.OnClickListener, CreatePassView {

    private static final String TAG = SignUp2Fragment.class.getSimpleName();
    ButtonPlus btnFinish;
    EditTextViewPlus edtSignUpPass1, edtSignUpPass2;
    private LoginListener listener;
    private CreatePassPresenter createPassPresenter;
    private SignUp1Fragment.SignUpListener signUpListener;

    public SignUp2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPassPresenter = new CreatePassImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFinish = (ButtonPlus) view.findViewById(R.id.btnSignUpPassword);
        edtSignUpPass1 = (EditTextViewPlus) view.findViewById(R.id.edtSignUpPass1);
        edtSignUpPass2 = (EditTextViewPlus) view.findViewById(R.id.edtSignUpPass2);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUpPassword:
                if (createPassPresenter != null) {
                    createPassPresenter.createPass(edtSignUpPass1.getText().toString());
                }
                break;
        }
    }

    private void gotoSignIn() {
        this.listener.gotoSignIn();
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    public void setSignUpListener(SignUp1Fragment.SignUpListener signUpListener) {
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
            final String errorMsg = ((ConsumerPasswordCreation) error).Description;
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

            DialogFactory.createMessageDialogWithListener(getActivity(),
                    getString(R.string.notify_register_success),
                    new DialogFactory.DialogListener() {
                        @Override
                        public void gotoSignInPage() {
                            signUpListener.removeFragment();
                            signUpListener.addFragment();
                            gotoSignIn();
                        }
                    });
            }
        });
    }
}
