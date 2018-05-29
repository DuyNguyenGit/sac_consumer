package com.sacombank.consumers.views.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.models.staticmodels.TabModel;

/**
 * Created by DUY on 4/6/2017.
 */

public class TabCustom extends RelativeLayout {

    private View mRootView;
    private ImageView imgIcon;
    private TextView tvTitle;

    public TabCustom(Context context) {
        super(context);
        initializeViews(context);
    }

    public TabCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public TabCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    void initializeViews(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = layoutInflater.inflate(R.layout.bottom_tab_custom, null);
        addView(mRootView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imgIcon = (ImageView) mRootView.findViewById(R.id.tab_image);
        tvTitle = (TextView) mRootView.findViewById(R.id.tab_title);
    }

    public void updateUI(TabModel tabModel, boolean isSelected) {
        if (isSelected) {
            findViewById(R.id.layout_shadow).setVisibility(VISIBLE);
            setBackgroundColor(getResources().getColor(R.color.white));
            tvTitle.setTextColor(getResources().getColor(R.color.colorBackgroundNavigation));
            imgIcon.setImageResource(tabModel.getEnableDrawableImageId());
        } else {
            findViewById(R.id.layout_shadow).setVisibility(GONE);
            setBackgroundColor(getResources().getColor(R.color.colorBackgroundNavigation));
            tvTitle.setTextColor(getResources().getColor(R.color.white));
            imgIcon.setImageResource(tabModel.getDisableDrawableImageId());
        }
        tvTitle.setText(tabModel.getTitle());
    }
}
