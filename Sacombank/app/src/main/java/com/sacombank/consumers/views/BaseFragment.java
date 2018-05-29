package com.sacombank.consumers.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public void setBaseListener(BaseListener listener) {
        this.listener = listener;
    }

    public BaseListener getBaseListener() {
        return listener;
    }

    BaseListener listener;

    public interface BaseListener {
        void updateNavigationBar(String fragmentClass, String title);
    }

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    protected void updateCommonUI(String fragmentClass, String title) {
        this.listener.updateNavigationBar(fragmentClass, title);
    }

    protected void showLoadingPage() {
        if (getActivity() != null)
            ((MainActivity) getActivity()).showProgressBar();
    }

    protected void hideLoadingPage() {
        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressBar();
    }


    protected SacombankApp getApp() {
        return (SacombankApp) getActivity().getApplication();
    }


}
