package com.sacombank.consumers.views.notifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment {


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(NotificationFragment.class.getSimpleName(), "Thông báo");
    }


}
