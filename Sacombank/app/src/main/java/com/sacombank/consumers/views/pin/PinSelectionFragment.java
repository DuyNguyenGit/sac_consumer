package com.sacombank.consumers.views.pin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;

/**
 * Created by TranVietThuc on 13/08/2017.
 */

public class PinSelectionFragment extends BaseFragment {
    private Button btn_Selection_XacNhan;
    public PinSelectionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pinselection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);
    }

    private void addControllers(View view) {
        btn_Selection_XacNhan= (Button) view.findViewById(R.id.btn_Selection_XacNhan);
    }

    private void addEvents(final View view) {
        btn_Selection_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        View viewDiaLog=LayoutInflater.from(getActivity()).inflate(R.layout.my_dialog,null);
        TextView txtTitleDialog= (TextView) viewDiaLog.findViewById(R.id.txtTitleDialog);
        Button btn_OK_Dialog= (Button) viewDiaLog.findViewById(R.id.btn_OK_Dialog);
        txtTitleDialog.setText(R.string.dialogTitle);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(viewDiaLog);

        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
        btn_OK_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}
