package com.sacombank.consumers.views.introduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;

/**
 * Created by TranVietThuc on 04/08/2017.
 */

public class IntroduceFragment extends BaseFragment {
    private ImageView introduceImage;
    private TextView introduceDeatail;
    private Toolbar toolbar;
    public  IntroduceFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introduce, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(IntroduceFragment.class.getSimpleName(), "Giới thiệu");
    }
}
