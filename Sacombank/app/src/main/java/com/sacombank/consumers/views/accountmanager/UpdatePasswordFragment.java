package com.sacombank.consumers.views.accountmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;
import com.sacombank.consumers.presenter.accountmanager.UpdatePassImpl;
import com.sacombank.consumers.presenter.accountmanager.UpdatePassPresenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerPasswordChange;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePasswordFragment extends BaseFragment implements UpdatePassView {

    private static final String TAG = UpdatePasswordFragment.class.getSimpleName();
    Button xacNhanUpdatePassword;
    EditTextViewPlus edtOldPass, edtNewPass1, edtNewPass2;
    private UpdatePassPresenter updatePassPresenter;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePassPresenter = new UpdatePassImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_password, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
    }

    private void addEvents(View view) {
        xacNhanUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updatePassPresenter != null) {
                    String oldPass = edtOldPass.getText().toString();
                    String newPass = edtNewPass2.getText().toString();
                    PasswordUpdating passwordUpdating = new PasswordUpdating(oldPass, newPass);
                    updatePassPresenter.updatePassword(passwordUpdating);
                }

            }
        });
    }

    private void addControllers(View view) {
        xacNhanUpdatePassword = (Button) view.findViewById(R.id.btnXacNhanUpdatePassWord);
        edtOldPass = (EditTextViewPlus) view.findViewById(R.id.edtPasswordOld);
        edtNewPass1 = (EditTextViewPlus) view.findViewById(R.id.edtPasswordNew1);
        edtNewPass2 = (EditTextViewPlus) view.findViewById(R.id.edtPasswordNew2);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(UpdatePasswordFragment.class.getSimpleName(), getResources().getString(R.string.title_update_pass));

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
            final String errorMsg = ((ConsumerPasswordChange) error).Description;
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
        final String successMsg = ((ConsumerPasswordChange) data).Description;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingPage();
                DialogFactory.createMessageDialogUpdatePassword(getContext(), successMsg, new DialogFactory.DialogListener.UpdatePassDialogListener() {
                    @Override
                    public void updatePassDone() {

                    }
                });
            }
        });
    }
}
