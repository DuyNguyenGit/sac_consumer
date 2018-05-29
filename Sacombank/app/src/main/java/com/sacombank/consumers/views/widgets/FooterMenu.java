package com.sacombank.consumers.views.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sacombank.consumers.R;

/**
 * Created by DUY on 8/8/2017.
 */

public class FooterMenu extends LinearLayout implements View.OnClickListener {

    private View mRootView;
    private RelativeLayout tab1, tab2, tab3, tab4;
    private ImageView imgTab1, imgTab2, imgTab3, imgTab4;
    private TextView tv1, tv2, tv3, tv4;
    private OnTabClickListener listener;

    public FooterMenu(Context context) {
        super(context);

        initializeViews(context);
    }

    public FooterMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        initializeViews(context);
    }

    public FooterMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initializeViews(context);
    }

    void initializeViews(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = layoutInflater.inflate(R.layout.tabbar_footer, null);
        addView(mRootView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateUI();
    }

    private void updateUI() {
        tab1 = (RelativeLayout) findViewById(R.id.layout_history);
        tab2 = (RelativeLayout) findViewById(R.id.layout_qr_scan);
        tab3 = (RelativeLayout) findViewById(R.id.layout_payment);
        tab4 = (RelativeLayout) findViewById(R.id.layout_transaction);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

        imgTab1 = (ImageView) findViewById(R.id.imgTab1);
        imgTab2 = (ImageView) findViewById(R.id.imgTab2);
        imgTab3 = (ImageView) findViewById(R.id.imgTab3);
        imgTab4 = (ImageView) findViewById(R.id.imgTab4);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
    }

    public void disable(){
        tab1.setEnabled(false);
        tab2.setEnabled(false);
        tab3.setEnabled(false);
        tab4.setEnabled(false);
        tv1.setTextColor(getResources().getColor(R.color.disable_button_text_color));
        tv2.setTextColor(getResources().getColor(R.color.disable_button_text_color));
        tv3.setTextColor(getResources().getColor(R.color.disable_button_text_color));
        tv4.setTextColor(getResources().getColor(R.color.disable_button_text_color));

        imgTab1.setImageResource(R.drawable.ic_footer_historynotlogin);
        imgTab2.setImageResource(R.drawable.ic_bottom_qrnotlogin);
        imgTab3.setImageResource(R.drawable.ic_bottom_paymentnotlogin);
        imgTab4.setImageResource(R.drawable.ic_chuyenkhoannotlogin);
    }

    public void enable(){
        tab1.setEnabled(true);
        tab2.setEnabled(true);
        tab3.setEnabled(true);
        tab4.setEnabled(true);
        tv1.setTextColor(getResources().getColor(R.color.white));
        tv2.setTextColor(getResources().getColor(R.color.white));
        tv3.setTextColor(getResources().getColor(R.color.white));
        tv4.setTextColor(getResources().getColor(R.color.white));

        imgTab1.setImageResource(R.drawable.ic_footer_history);
        imgTab2.setImageResource(R.drawable.ic_bottom_qr);
        imgTab3.setImageResource(R.drawable.ic_bottom_payment);
        imgTab4.setImageResource(R.drawable.ic_chuyenkhoan);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_history:
                listener.onTabClick(0);
                break;
            case R.id.layout_qr_scan:
                listener.onTabClick(1);
                break;
            case R.id.layout_payment:
                listener.onTabClick(2);
                break;
            case R.id.layout_transaction:
                listener.onTabClick(3);
                break;
        }
    }

    public interface OnTabClickListener{
        void onTabClick(int position);
    }
}
