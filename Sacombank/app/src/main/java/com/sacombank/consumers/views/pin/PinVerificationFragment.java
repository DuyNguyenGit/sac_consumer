package com.sacombank.consumers.views.pin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;

/**
 * Created by TranVietThuc on 13/08/2017.
 */

public class PinVerificationFragment extends BaseFragment {
    private TextView txtVerification_Pin;

    public PinVerificationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pinverification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
    }

    private void addControllers(View view) {
        txtVerification_Pin = (TextView) view.findViewById(R.id.txtVerification_Pin);
    }

    private void addEvents(View view) {

    }

}
