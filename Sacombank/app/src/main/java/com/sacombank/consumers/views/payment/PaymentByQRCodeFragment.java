package com.sacombank.consumers.views.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
//<<<<<<< HEAD:Sacombank/app/src/main/java/com/sacombank/consumers/qr/PaymentByQRCodeFragment.java

//=======
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.home.HomeFragment;
//>>>>>>> 69e8dde033903a538d9712c39711f4d103732918:Sacombank/app/src/main/java/com/sacombank/consumers/views/qr/PaymentByQRCodeFragment.java

/**
 * Created by Truong Thien on 14/08/2017.
 */

public class PaymentByQRCodeFragment extends BaseFragment {
    private Spinner spinner_Code;
    private ArrayAdapter<String> adapter;
    private String[] list={"Master","Visa"};
    private Button btnTrangChu;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(PaymentByQRCodeFragment.class.getSimpleName(), "Thanh toán QR");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_by_qr_code_finish,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvent();
    }

    private void addEvent() {
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPushFragment(new HomeFragment(),getBaseListener());
            }
        });
    }

    private void addControllers(View view) {
        btnTrangChu= (Button) view.findViewById(R.id.btnTrangChu);
        spinner_Code= (Spinner) view.findViewById(R.id.spinner_Code);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        spinner_Code.setAdapter(adapter);
    }

    private void processPushFragment(BaseFragment fragment, BaseFragment.BaseListener baseListener) {
        if (fragment == null) return;
        fragment.setBaseListener(baseListener);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.acitivity_in_from_right_to_left, R.anim.activity_out_from_left);
        fragmentTransaction.replace(R.id.main_content, fragment);
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
