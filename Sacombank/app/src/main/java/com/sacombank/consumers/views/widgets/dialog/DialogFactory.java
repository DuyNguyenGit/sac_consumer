package com.sacombank.consumers.views.widgets.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.home.HomeFragment;


/**
 * Created by DUY on 8/3/2017.
 */

public class DialogFactory {


    public interface DialogListener {
        void gotoSignInPage();

        interface UpdatePassDialogListener {
            void updatePassDone();
        }

        interface LogoutListener {
            void logOut();
        }
        interface UpdateCardDefaultListener {
            void updateCArd();
        }

        public interface PasswordInputListener {

            void setPassword(String s);
        }
    }

    public static void createMessageDialog(Context context, String message) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_notify);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContent);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btYes);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void createMessageDialogWithListener(Context context, String message, final DialogListener dialogListener) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_notify);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContent);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btYes);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListener.gotoSignInPage();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void createMessageDialogUpdatePassword(final Context context, String message, final DialogListener.UpdatePassDialogListener listener) {
        final com.sacombank.consumers.views.widgets.TranslucentDialog dialog = new com.sacombank.consumers.views.widgets.TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_update_password);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContentUpdatePassword);
        tvMessage.setText(message);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btn_OK_DialogUpdatePassword);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.updatePassDone();
            }
        });
        dialog.show();
    }



    public static void createMessageDialogLogout(Context context, String message, final DialogListener.LogoutListener listener) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContent);
        tvMessage.setText(message);
        TextView btnYes = (TextView) dialog.findViewById(R.id.btYes);
        TextView btnNo = (TextView) dialog.findViewById(R.id.btNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.logOut();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void createMessageDialogUpdateCardDefault(Context context, String message, final DialogListener.UpdateCardDefaultListener listener) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_update_card);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContent);
        tvMessage.setText(message);
        TextView btnYes = (TextView) dialog.findViewById(R.id.btYes);
        TextView btnNo = (TextView) dialog.findViewById(R.id.btNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateCArd();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public static void createPaswordInputDialog(Context context, String message, final DialogListener.PasswordInputListener listener) {
        final TranslucentDialog dialog = new TranslucentDialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_input_password);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.txtContent);
        //tvMessage.setText(message);
        final EditText edtPass = (EditText) dialog.findViewById(R.id.edt);
        TextView btnQuit = (TextView) dialog.findViewById(R.id.btYes);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.setPassword(edtPass.getText().toString());
            }
        });
        dialog.show();
    }
    public void createMessageDialogEnterQRCode(Context context,String message, final DialogListener.PasswordInputListener listener) {
        final com.sacombank.consumers.views.widgets.TranslucentDialog dialog = new com.sacombank.consumers.views.widgets.TranslucentDialog(context);
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
}
