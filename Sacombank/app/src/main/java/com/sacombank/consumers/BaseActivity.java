package com.sacombank.consumers;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.views.BaseFragment;


public abstract class BaseActivity extends AppCompatActivity implements BaseCallback, View.OnClickListener, BaseFragment.BaseListener {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private FrameLayout baseLayout;
    private FrameLayout coverLayout;
    private FrameLayout contentLayout;
    private RelativeLayout toolBar;
    private ImageView imgHome;
    private ImageView imgLock;
    BaseFragment currentFragment = new BaseFragment();
    private RelativeLayout titleBar;
    private RelativeLayout searchBar;
    private ImageView imgCloseIcon;
    protected boolean currentPageIsHome = true;

    protected boolean isSearchOpening;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        baseLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        coverLayout = (FrameLayout) baseLayout.findViewById(R.id.cover_layout_common);
        contentLayout = (FrameLayout) baseLayout.findViewById(R.id.content_layout);
        toolBar = (RelativeLayout) baseLayout.findViewById(R.id.toolBar);
        titleBar = (RelativeLayout) baseLayout.findViewById(R.id.layout_title_bar);
        searchBar = (RelativeLayout) baseLayout.findViewById(R.id.layout_search_bar);

        imgHome = (ImageView) baseLayout.findViewById(R.id.imgHome);
        imgLock = (ImageView) baseLayout.findViewById(R.id.imgLock);
        imgCloseIcon = (ImageView) baseLayout.findViewById(R.id.imgCloseIcon);
        imgHome.setOnClickListener(this);
        imgLock.setOnClickListener(this);
        imgCloseIcon.setOnClickListener(this);

        getLayoutInflater().inflate(layoutResID, contentLayout, true);
        super.setContentView(baseLayout);
    }

    protected boolean userIsLogined() {
        return ApiManager.getAccountObject() != null;
    }

    protected void clearDataLocal() {
        ApiManager.clearAllData();
    }

    protected void showSearchBar() {
        titleBar.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);
        isSearchOpening = true;
    }

    private void closeSearchPage() {
        hideSearchBar();
        onCloseSearchPage();
    }

    protected void hideSearchBar() {
        isSearchOpening = false;
        titleBar.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.GONE);
    }


    @Override
    public void showProgressBar() {
        coverLayout.setVisibility(View.VISIBLE);
        Log.e(TAG, "showProgressBar: BaseActivity");
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                coverLayout.setVisibility(View.GONE);
            }
        });
        Log.e(TAG, "hideProgressBar: BaseActivity");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                toggleMenu();
                break;
            case R.id.imgLock:
                onClickRightIcon();
                break;
            case R.id.imgCloseIcon:
                closeSearchPage();
                break;
        }
    }

    protected abstract void onClickRightIcon();

    protected abstract void onCloseSearchPage();

    protected abstract void onOpenSearchPage();

    protected abstract void toggleMenu();

    public void changeIconHome(int id) {
        imgLock.setImageResource(id);
        currentPageIsHome = (id == R.drawable.search);
    }

}
