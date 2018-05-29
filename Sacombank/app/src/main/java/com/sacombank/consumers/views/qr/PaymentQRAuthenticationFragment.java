package com.sacombank.consumers.views.qr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;

/**
 * Created by TranVietThuc on 14/08/2017.
 */

public class PaymentQRAuthenticationFragment extends BaseFragment {
    private Button btn_QR_ThanhToan;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paymentqrauthentication,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
    }

    private void addEvents(final View view) {

    }

    private void addControllers(View view) {
        btn_QR_ThanhToan= (Button) view.findViewById(R.id.btn_QR_ThanhToan);

    }

}
