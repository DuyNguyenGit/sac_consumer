package com.sacombank.consumers.views.loginregister.resetpassword;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.loginregister.LoginListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    private LoginListener listener;
    Button btnYes, btnNo;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNo = (Button) view.findViewById(R.id.btnNo);
        btnYes = (Button) view.findViewById(R.id.btnYes);
        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNo:
                listener.gotoSignIn();
                break;
            case R.id.btnYes:
                listener.gotoSignUp();
                break;

        }
    }
}
