package com.sacombank.consumers.views.cardmanagement;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.presenter.cardmanagement.BalanceImpl;
import com.sacombank.consumers.presenter.cardmanagement.BalancePresenter;
import com.sacombank.consumers.utils.DateUtil;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.cardmanagement.view.BalanceView;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerCardBalanceInquiry;
import com.stb.api.bo.consumer.ConsumerPasswordChange;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends CardBaseFragment implements BalanceView {


    private static final String TAG = BalanceFragment.class.getSimpleName();

    private TextView tvBalance, tvCardNumber, tvDueDate;
    private BalancePresenter presenter;

    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BalanceImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBalance = (TextView) view.findViewById(R.id.txtSoDuTheSurPus);
        tvCardNumber = (TextView) view.findViewById(R.id.txtSoTheSurPus);
        tvDueDate = (TextView) view.findViewById(R.id.txtNgayHetHanSurPus);
    }

    @Override
    protected void updateUI(CardObject cardObject) {
        Log.e(TAG, "updateUI: >>>" + new Gson().toJson(cardObject));
        tvCardNumber.setText(cardObject.CardNumber);
        //TODO : Bổ sung API sau cho Due date, tạm thời set Rỗng
        tvDueDate.setText("");

        if (presenter != null) {
            presenter.getBalance(cardObject);
        }
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
            final String errorMessage = ((ConsumerCardBalanceInquiry) error).Description;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogFactory.createMessageDialog(getActivity(), errorMessage);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onSuccess(final Object data) {
        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(data));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateBalance(((ConsumerCardBalanceInquiry) data));
            }
        });
    }

    private void updateBalance(ConsumerCardBalanceInquiry data) {
        tvBalance.setText(data.AvailableBalance);
    }
}
