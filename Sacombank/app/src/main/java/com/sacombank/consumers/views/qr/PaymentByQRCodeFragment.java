package com.sacombank.consumers.views.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.presenter.qr.PaymentByQRCodePresenter;
import com.sacombank.consumers.presenter.qr.PaymentByQRCodePresenterImpl;
import com.sacombank.consumers.utils.DateUtil;
import com.sacombank.consumers.utils.Utils;
import com.sacombank.consumers.utils.constant.Constant;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.home.category.CategoryFragment;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.stb.api.bo.consumer.ConsumerQRPayment;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Truong Thien on 14/08/2017.
 */

public class PaymentByQRCodeFragment extends BaseFragment implements PaymentByQRCodeView, AdapterView.OnItemSelectedListener {
    //define
    private static final String KEY_SCAN_RESULT = "scan_result";
    private String TAG = PaymentByQRCodeFragment.class.getSimpleName();
    private final char QRSTatic = '1';
    private final char QRSDynamic = '2';
    private TextView txtOrderNumber,txtPayAt, txtTradingDate;
    private EditText edtAmount;
    private Spinner spinner_Code;
    private Button btnContinueQRCode;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private PushPaymentData mPaymentData;
    private CardObject mSelectedCardObject;
    private HashMap<String, CardObject> mDictCardObjects;

    private PaymentByQRCodePresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String scanResult = getArguments().getString(KEY_SCAN_RESULT);
        Log.d(TAG,"scan result = " + scanResult);
        mPaymentData  = Utils.QRCodePayment.parseQRCode(scanResult);
    }


    public static PaymentByQRCodeFragment newInstance(String scanResult) {
        PaymentByQRCodeFragment paymentByQRCodeFragment = new PaymentByQRCodeFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SCAN_RESULT, scanResult);
        paymentByQRCodeFragment.setArguments(args);

        return paymentByQRCodeFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(PaymentByQRCodeFragment.class.getSimpleName(), "Thanh toán QR");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_by_qr_code,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
        setDefaultValuesToControllers();
        presenter = new PaymentByQRCodePresenterImpl(this);
    }

    private void addEvents(View view) {
        btnContinueQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogFactory().createMessageDialogEnterQRCode(getContext(), getResources().getString(R.string.dialog_message_password), new DialogFactory.DialogListener.PasswordInputListener() {
                    @Override
                    public void setPassword(String s) {
                        if (presenter != null) {
                            presenter.validatePaymentByQRCode(mPaymentData, mSelectedCardObject, s);
                        }
                    }
                });
            }
        });

        spinner_Code.setOnItemSelectedListener(this);
    }

    private void setValuesForVisaMasterCard(){
        String visa = mPaymentData.getMerchantIdentifierVisa02();
        if (visa == null) {
            visa = mPaymentData.getMerchantIdentifierVisa03();
        }

        String master = mPaymentData.getMerchantIdentifierMastercard04();
        if (master == null) {
            master = mPaymentData.getMerchantIdentifierMastercard05();
        }

        String visaMaster = null;
        if (visa != null && master != null) {
            visaMaster = null;
        }else if (visa != null) {
            visaMaster = ApiManager.CardIssuer.Visa.toString();
        }else if (master != null) {
            visaMaster = ApiManager.CardIssuer.Mastercard.toString();
        }

        List<CardObject> cardObjectList = ApiManager.getVisaMaterCardList(visaMaster);
        list = new ArrayList<String>();
        mDictCardObjects = new HashMap<String, CardObject>();
        CardObject cardObject;
        for (int i = 0; i < cardObjectList.size(); i++) {
            cardObject = cardObjectList.get(i);
            list.add(cardObject.VisaMasterCard);
            mDictCardObjects.put(cardObject.VisaMasterCard, cardObject);
        }

        if (list.size() > 0) {
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
            spinner_Code.setAdapter(adapter);
        }else{
            DialogFactory.createMessageDialog(getActivity(), getResources().getString(R.string.dialog_message_no_matching_card));
            btnContinueQRCode.setText(getResources().getString(R.string.close));
            btnContinueQRCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToHomePage();
                }
            });
        }
    }

    private void setDefaultValuesToControllers(){
        try {
            if (mPaymentData.getPointOfInitiationMethod() != null) {
                char QRType = mPaymentData.getPointOfInitiationMethod().charAt(1); // DialogFactory.createMessageDialog(getContext(),e.getMessage());
                Log.d(TAG, "QRType = " + QRType);
                if (QRType == QRSDynamic) {
                    txtOrderNumber.setText(mPaymentData.getAdditionalData().getBillNumber());
                    edtAmount.setText(mPaymentData.getTransactionAmount().toString());
                    edtAmount.setEnabled(false);
                } else if (QRType == QRSTatic) {
                    txtOrderNumber.setText(mPaymentData.getAdditionalData().getTerminalId());
                    edtAmount.setEnabled(true);
                    edtAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    edtAmount.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtAmount, InputMethodManager.SHOW_IMPLICIT);
                }

                txtPayAt.setText(mPaymentData.getMerchantName());
                txtTradingDate.setText(DateUtil.getCurrentDateTime("dd/MM/yyyy"));
                setValuesForVisaMasterCard();
            }
        }catch (Exception e){
            DialogFactory.createMessageDialog(getContext(),getString(R.string.incorrect_qr_code));
        }

    }

    private void addControllers(View view) {
        btnContinueQRCode = (Button) view.findViewById(R.id.btnContinueQRCode);
        txtOrderNumber = (TextView) view.findViewById(R.id.txtOrderNumber);
        edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        txtPayAt = (TextView) view.findViewById(R.id.txtPayAt);
        txtTradingDate = (TextView) view.findViewById(R.id.txtTradingDate);
        spinner_Code= (Spinner) view.findViewById(R.id.spinner_Code);
    }

    @Override
    public void goToHomePage() {
        ((MainActivity) getActivity()).onBackHomePage();
    }

    private void lockPayment() {
        edtAmount.setEnabled(false);
        btnContinueQRCode.setText(getResources().getString(R.string.home));
        btnContinueQRCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToHomePage();
            }
        });
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
        final String errorMessage = ((ConsumerQRPayment)error).Description;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogFactory.createMessageDialog(getActivity(), errorMessage);
            }
        });
    }

    @Override
    public void onSuccess(Object data) {
        final String message = ((ConsumerQRPayment)data).Description;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogFactory.createMessageDialog(getActivity(), message);
                lockPayment();
            }
        });
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String keyVisaMaster = list.get(i);
        mSelectedCardObject = mDictCardObjects.get(keyVisaMaster);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
