package com.sacombank.consumers.views.cardmanagement;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.CardMaintenance;
import com.sacombank.consumers.presenter.cardmanagement.CardUpdateImpl;
import com.sacombank.consumers.presenter.cardmanagement.CardUpdatePresenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.cardmanagement.view.CardUpdateView;
import com.sacombank.consumers.views.widgets.SwitchPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerCardBalanceInquiry;
import com.stb.api.bo.consumer.ConsumerCardMaintenance;
import com.stb.api.bo.consumer.ConsumerCardUpdate;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonInfoFragment extends CardBaseFragment implements CardUpdateView, View.OnTouchListener {

    Switch swDefault, swActive;
    TextView tvCardNumer;
    CardUpdatePresenter presenter;
    private boolean isFirsTime = true;


    private static final String TAG = CommonInfoFragment.class.getSimpleName();

    public CommonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CardUpdateImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swDefault = (SwitchPlus) view.findViewById(R.id.SwitchTheMacDinh);
        swActive = (SwitchPlus) view.findViewById(R.id.SwitchTheHoatDong);
        tvCardNumer = (TextView) view.findViewById(R.id.txtSoThe);
        swDefault.setOnTouchListener(this);
        swActive.setOnTouchListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        isFirsTime = false;
    }

    @Override
    protected void updateUI(CardObject cardObject) {
        Log.e(TAG, "updateUI: >>>" + new Gson().toJson(cardObject));

        if (!isFirsTime) {
            return;
        }

        this.cardObject = cardObject;
        if (cardObject !=null && cardObject.DefaultIndicator != null) {
            swDefault.setChecked((boolean)cardObject.DefaultIndicator);
            swActive.setChecked(TextUtils.isEmpty(cardObject.CardStatus));
            tvCardNumer.setText(cardObject.CardNumber);
            swDefault.setChecked(cardObject.DefaultIndicator);
            swActive.setChecked(cardObject.CardStatus.equalsIgnoreCase(ApiManager.CardStatus.ACTIVE.toString()));
        }
    }

    // THẻ hoạt động
    private void requestCardMaintainance() {
        Log.e(TAG, "requestCardMaintainance: >>>");
        DialogFactory.createPaswordInputDialog(getActivity(), getResources().getString(R.string.dialog_message_password),
                new DialogFactory.DialogListener.PasswordInputListener() {
                    @Override
                    public void setPassword(String password) {
                        if (presenter != null) {
                            cardObject.CardStatus = !swDefault.isChecked() ? ApiManager.CardStatus.ACTIVE.toString() : ApiManager.CardStatus.INACTIVE.toString();
                            CardMaintenance cardMaintenance = new CardMaintenance(cardObject, password);
                            presenter.updateCardMaintenance(cardMaintenance);
                        }
                    }
                });
    }

    // Thẻ Mặc định
    private void requestCardUpdate() {
        Log.e(TAG, "requestCardUpdate: >>>");
        DialogFactory.createMessageDialogUpdateCardDefault(getActivity(), getResources().getString(R.string.dialog_message_confirm_update_card),
                new DialogFactory.DialogListener.UpdateCardDefaultListener() {
                    @Override
                    public void updateCArd() {
                        // Call API
                        if (presenter != null) {
                            cardObject.DefaultIndicator = !swDefault.isChecked();
                            presenter.updateCard(cardObject);
                        }
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
    public void onError(final Object error) {
        Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
        if (error != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error instanceof ConsumerCardUpdate) {

                        Log.e(TAG, "run: >>>error instance of ConsumerCardUpdate");
                        final String errorMsg = ((ConsumerCardUpdate) error).Description;
                        updateUI(cardObject);
                        DialogFactory.createMessageDialog(getActivity(), errorMsg);

                    } else if (error instanceof ConsumerCardMaintenance) {
                        final String errorMsg = ((ConsumerCardMaintenance) error).Description;
                        updateUI(cardObject);
                        DialogFactory.createMessageDialog(getActivity(), errorMsg);

                    }
                }
            });

        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                    updateUI(cardObject);
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

                if (data instanceof ConsumerCardUpdate) {
                    Log.e(TAG, "run: >>>data instance of ConsumerCardUpdate");
                    String successMsg = ((ConsumerCardUpdate) data).Description;
                    updateCardDeafultStatus(((ConsumerCardUpdate) data));
                } else if (data instanceof ConsumerCardMaintenance) {
                    Log.e(TAG, "run: >>>data instance of ConsumerCardMaintenance");
                    String successMsg = ((ConsumerCardMaintenance) data).Description;
                    updateStatusCardMaintenance(((ConsumerCardMaintenance) data));
                }
            }
        });
    }

    private void updateStatusCardMaintenance(ConsumerCardMaintenance data) {
        cardObject.CardStatus = data.CardStatus;
        updateUI(cardObject);
    }

    private void updateCardDeafultStatus(ConsumerCardUpdate data) {
        cardObject.DefaultIndicator = data.DefaultIndicator;
        updateUI(cardObject);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            switch (v.getId()) {
                case R.id.SwitchTheMacDinh:
                    requestCardUpdate();
                    break;
                case R.id.SwitchTheHoatDong:
                    requestCardMaintainance();
                    break;
            }
            return true;
        }
        return false;
    }

}
