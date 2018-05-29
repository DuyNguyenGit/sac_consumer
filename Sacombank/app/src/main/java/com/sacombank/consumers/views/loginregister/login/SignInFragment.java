package com.sacombank.consumers.views.loginregister.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.models.jsonobjects.LoginData;
import com.sacombank.consumers.models.jsonobjects.SignInError;
import com.sacombank.consumers.models.jsonobjects.User;
import com.sacombank.consumers.presenter.loginregister.login.SignInImpl;
import com.sacombank.consumers.presenter.loginregister.login.SignInPresenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.MerchantAccountLogin;
import com.stb.api.bo.consumer.ConsumerAccountLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment implements View.OnClickListener, SignInView {
    public static final String TAG = SignInFragment.class.getSimpleName();

    EditTextViewPlus edtUsername;
    EditText edtPassword;
    Button btnLogin;
    TextView tvSkip;
    private SignInListener mListener;
    private SignInPresenter signInPresenter;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void showLoading() {
        mListener.showLoading();
    }

    @Override
    public void hideLoading() {
        mListener.hideLoading();
    }

    public void setListener(SignInListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onError(Object signInError) {
        if (signInError != null) {
            final String errorMsg = ((ConsumerAccountLogin) signInError).Description;
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
    public void onSuccess(final Object user) {
        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(user));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingPage();
                mListener.onLoginSuccess(user);
            }
        });

    }

    public interface SignInListener<T> {
        void showLoading();
        void hideLoading();
        void onLoginSuccess(T user);
        void onSkip();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (signInPresenter == null) {
            signInPresenter = new SignInImpl(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = (EditTextViewPlus) view.findViewById(R.id.edtEmail);
        edtPassword = (EditTextViewPlus) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        tvSkip = (TextView) view.findViewById(R.id.tvSkip);

        btnLogin.setOnClickListener(this);
        tvSkip.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                showLoadingPage();
                String firebaseToken = ((SacombankApp) getActivity().getApplication()).firebaseTokenPref.getValue();
                signInPresenter.login(new LoginData(edtUsername.getText().toString(), edtPassword.getText().toString(), firebaseToken));
                break;
            case R.id.tvSkip:
                mListener.onSkip();
                break;
        }
    }
}
