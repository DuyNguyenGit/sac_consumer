package com.sacombank.consumers.views.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.accountmanager.UpdatePasswordFragment;
import com.sacombank.consumers.views.home.HomeFragment;
import com.sacombank.consumers.views.loginregister.LoginFragment;
import com.sacombank.consumers.views.payment.PaymentByQRCodeFragment;

/**
 * Created by DUY on 8/3/2017.
 */

public class DialogFactory {
    public interface DialogListener {
        public interface PasswordInputListener {
            void setPassword(String s);
        }
    }

    public static void createMessageDialogRegister(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentRegister);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogRegister);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    public static void createMessageDialogPinSelection(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_pin_selection);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentPinSelection);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogPinSelection);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void createMessageDialogDiviceSwitch(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_divice_switch);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentDiviceSwitch);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogDiviceSwitch);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void createMessageDialogEnterPinCard(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_card);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentCard);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogQRCode);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void createMessageDialogEnterQRCode1(Context context, final FragmentActivity fragmentActivity, final DialogFactory.DialogListener.PasswordInputListener listener) {
        final TranslucentDialog dialog = new TranslucentDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_qrcode);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentQRCode1);
        //tvMessage.setText(message);
        final EditText enterQRCode = (EditText) dialog.findViewById(R.id.edEnterQRCode);
       // enterQRCode.setHint(hintQRCode);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_HoanTat_DialogEnterPassword);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.setPassword(enterQRCode.getText().toString());
            }
        });
        dialog.show();
    }

    public static void createMessageDialogLoginOnDiviceAnother(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_login_on_divice_another);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentLoginOnDiviceAnother);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogLoginOnDiviceAnother);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void processPushFragment(BaseFragment fragment, FragmentActivity fragmentActivity, BaseFragment.BaseListener baseListener) {
        if (fragment == null) return;
        fragment.setBaseListener(baseListener);
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.acitivity_in_from_right_to_left, R.anim.activity_out_from_left);
        fragmentTransaction.replace(R.id.main_content, fragment);
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
