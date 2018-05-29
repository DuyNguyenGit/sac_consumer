package com.sacombank.consumers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sacombank.consumers.adapters.NavigationDrawerAdapter;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.sidemenu.Section;
import com.sacombank.consumers.models.sidemenu.SectionItem;
import com.sacombank.consumers.models.sidemenu.SideMenuDataFactory;
import com.sacombank.consumers.services.FirebaseIDTask;
import com.sacombank.consumers.utils.Utils;
import com.sacombank.consumers.utils.constant.Constant;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.account_information.AccountInformationFragment;
import com.sacombank.consumers.views.cardmanagement.CardManagerFragment;
import com.sacombank.consumers.views.accountmanager.UpdatePasswordFragment;
import com.sacombank.consumers.views.history.HistoryFragment;
import com.sacombank.consumers.views.home.HomeFragment;
import com.sacombank.consumers.views.home.category.ArticleDetailFragment;
import com.sacombank.consumers.views.introduce.IntroduceFragment;
import com.sacombank.consumers.views.loginregister.LoginFragment;
import com.sacombank.consumers.views.loginregister.OnLoginFragmentInteractionListener;
import com.sacombank.consumers.views.payment.Payment1Fragment;
import com.sacombank.consumers.views.payment.PaymentByQRCodeFragment;
import com.sacombank.consumers.views.pin.PinSelectionFragment;
import com.sacombank.consumers.views.policy.PolicyFragment;
import com.sacombank.consumers.views.qr.PaymentQRAuthenticationFragment;
import com.sacombank.consumers.views.qr.PaymentQRScannerFragment;
import com.sacombank.consumers.views.search.SearchFragment;
import com.sacombank.consumers.views.transaction.TransactionFragment;
import com.sacombank.consumers.views.widgets.FooterMenu;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.util.List;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, OnLoginFragmentInteractionListener, SearchFragment.SearchPageListener, View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private SlidingMenu mSlidingMenu;
    private FragmentManager fragmentManager;
    private NavigationDrawerAdapter adapter;
    private RelativeLayout layoutBack;
    private int mCurrentSectionID = -1;
    private String mCurrentTitle;
    private int mSystemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    FooterMenu footerMenu;
    private TextView tvTitle;
    private LinearLayout layoutRoot;
    private List<Section> mSlideMenuList;

    @Override
    public void onBackPressed() {
        mCurrentTitle="";
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_content);
        if(currentFragment instanceof HomeFragment){
            finish();
        }else if(fragmentManager.getBackStackEntryCount()>1){
            super.onBackPressed();
        }
        else if(fragmentManager.getBackStackEntryCount()==1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        mCurrentTitle="";
        switch (view.getId()) {
            case R.id.layout_back:
                if(fragmentManager.getBackStackEntryCount()>1){
                    onBackPressed();
                }else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("FCM_SCB");
        String token = FirebaseInstanceId.getInstance().getToken();
        new FirebaseIDTask().execute(token);

        setupDrawer();
        attachExpandableListView();
        initView();
        updateView();
    }

    private void updateView() {
        if(userIsLogined()){
            footerMenu.enable();
        }else {
            footerMenu.disable();
        }
    }

    @Override
    protected void onClickRightIcon() {
        if(!currentPageIsHome)
            pushFragmentByGroup(Constant.MENU_HOME);
        else {
            showSearchBar();
            onOpenSearchPage();
        }
    }

    @Override
    protected void onCloseSearchPage() {

    }

    @Override
    protected void onOpenSearchPage() {
        currentFragment = new SearchFragment();
        ((SearchFragment) currentFragment).setListener(this);
        processPushFragment(currentFragment);
    }


    @Override
    protected void toggleMenu() {
        mSlidingMenu.toggle();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        pushFragmentByGroup(Constant.MENU_HOME);
        layoutRoot = (LinearLayout) findViewById(R.id.root_layout);
        layoutBack = (RelativeLayout) findViewById(R.id.layout_back);
        layoutBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitleNav);
        footerMenu = (FooterMenu) findViewById(R.id.footer_menu);
        footerMenu.setOnTabClickListener(mOnTabClickListener);
        if(getAndroidVersion()> 19){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) footerMenu.getLayoutParams();
            params.setMargins(0, 0, 0, Utils.getSoftButtonsBarSizePort(this));
            footerMenu.setLayoutParams(params);
            footerMenu.requestLayout();
        }
        Log.e(TAG, "initView: >>>" + Utils.getSoftButtonsBarSizePort(this));
        //hideSystemUi();
    }

    public int getAndroidVersion() {
        String release = Build.VERSION.RELEASE;//4.4.2
        int sdkVersion = Build.VERSION.SDK_INT;//API 19
        return sdkVersion;
    }

    private void hideSystemUi() {
        // Set flags for hiding status bar and navigation bar
        mSystemUiVisibility = mSystemUiVisibility
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        layoutRoot.setSystemUiVisibility(mSystemUiVisibility);
    }

    /** Shows StatusBar and NavigationBar */
    private void showSystemUi() {
        // Reset flags for hiding status bar and navigation bar
        mSystemUiVisibility = mSystemUiVisibility
                & ~View.SYSTEM_UI_FLAG_FULLSCREEN
                & ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        layoutRoot.setSystemUiVisibility(mSystemUiVisibility);
    }

    public void showHideBack(boolean shouldShow, String title) {
        tvTitle.setText(title);
        int visible = shouldShow ? View.VISIBLE : View.GONE;
        if (layoutBack != null)
            layoutBack.setVisibility(visible);
    }

    private FooterMenu.OnTabClickListener mOnTabClickListener = new FooterMenu.OnTabClickListener() {

        @Override
        public void onTabClick(int position) {
            switch (position) {
                case Constant.FOOTER_NOTIFY:
                    mCurrentSectionID=4;
                    mCurrentTitle="";
                    Log.e(TAG, "onClick: Footer >>>" + position);
                    currentFragment = new HistoryFragment();
                    break;
                case Constant.FOOTER_SCAN_QRCODE:
                    mCurrentSectionID=-1;
                    mCurrentTitle="";
                    Log.e(TAG, "onClick: Footer >>>" + position);
                    currentFragment = new PaymentQRScannerFragment();
                    break;
                case Constant.FOOTER_PAYMENT:
                    mCurrentSectionID=-1;
                    mCurrentTitle="";
                    Log.e(TAG, "onClick: Footer >>>" + position);
                    currentFragment = new Payment1Fragment();
                    break;
                case Constant.FOOTER_TRANSACTION:
                    mCurrentSectionID=-1;
                    mCurrentTitle="";
                    Log.e(TAG, "onClick: Footer >>>" + position);
                    currentFragment = new TransactionFragment();
                    break;
                default:
                    mCurrentSectionID=-1;
                    mCurrentTitle="";
                    Log.e(TAG, "onClick: Footer >>>" + position);
                    break;
            }
            processPushFragment(currentFragment);
        }
    };

    private void setupDrawer() {
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setBackgroundResource(R.color.purple_dark);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
    }

    private NavigationDrawerAdapter.OnItemClickListener mChildItemClickListener = new NavigationDrawerAdapter.OnItemClickListener() {
        @Override
        public void onChildItemClick(Object section, Object item) {
            String title = ((SectionItem) item).getName();
            int sectionId = ((Section) section).getSectionId();
            Log.e(TAG, "onChildItemClick: >>> sectionId = " + sectionId);
            mSlidingMenu.toggle();
            pushFragmentByItem(sectionId, title);
        }

        @Override
        public void onGroupItemClick(Object section) {
            if (section instanceof Section) {
                int sectionId = ((Section) section).getSectionId();
                mSlidingMenu.toggle();
                pushFragmentByGroup(sectionId);
            }
        }
    };

    public void onNextDetailArticle(SuggestionObject suggestionObject) {
        getIntent().putExtra("DetailArticle",suggestionObject);
        currentFragment = new ArticleDetailFragment();
        processPushFragment(currentFragment);
    }

    public void onBackHomePage() {
        updateSlideMenu(userIsLogined());
        currentFragment = new HomeFragment();
        processPushFragment(currentFragment);
        updateView();
    }

    public void goLoginPage() {
        currentFragment = new LoginFragment();
        processPushFragment(currentFragment);
    }

    private void pushFragmentByGroup(int sectionId) {
        if (mCurrentSectionID == sectionId) {
            return;
        }
        switch (sectionId) {
            case Constant.MENU_HOME:
                currentFragment = new HomeFragment();
                break;
            case Constant.MENU_ONLINE:
                currentFragment = new BaseFragment();
                break;
            case Constant.MENU_LANGUAGE:
                currentFragment = new BaseFragment();
                break;
            case Constant.MENU_POLICY:
                currentFragment = new PolicyFragment();
                break;
            case Constant.MENU_HISTORY:
                currentFragment = new HistoryFragment();
                break;
            case Constant.MENU_ABOUT:
                currentFragment = new IntroduceFragment();
                break;
        }
        mCurrentSectionID = sectionId;
        processPushFragment(currentFragment);
    }

    private void pushFragmentByItem(int sectionId, String title) {
        if (mCurrentSectionID == sectionId && mCurrentTitle.equalsIgnoreCase(title)) {
            return;
        }
        switch (sectionId) {
            case Constant.MENU_ACCOUNT:
                switch (title) {
                    case "Đăng nhập/Đăng ký":
                    case "Login/Registry":
                        currentFragment = new LoginFragment();
                        break;
                    case "Đăng xuất":
                    case "Logout":
                        logout();
                        break;
                    case "Cập nhật mật khẩu":
                    case "Update password":
                        currentFragment = new UpdatePasswordFragment();
                        break;
                    case "Thông tin người dùng":
                    case "User information":
                        currentFragment = new AccountInformationFragment();
                        break;
                    case "Quản lý thẻ":
                    case "TagManagement":
                        currentFragment = new CardManagerFragment();
                        break;
                }
                break;
            case Constant.MENU_PAYMENT:
                switch (title) {
                    case "Thanh toán QR":
                        currentFragment = new PaymentQRScannerFragment();
                        break;
                    case "Thanh toán hóa đơn":
                        currentFragment = new PaymentByQRCodeFragment();
                        break;
                    case "Thanh toán thẻ tín dụng":
                        currentFragment = new PaymentQRAuthenticationFragment();
                        break;
                    case "Nạp tiền điện thoại":
                        currentFragment = new BaseFragment();
                        break;
                }
                break;
            case Constant.MENU_HISTORY:
                switch (title) {
                    case "Thanh toán qua QR":
                        currentFragment = new PaymentQRScannerFragment();
                        break;
                    case "Thanh toán hóa đơn":
                        currentFragment = new PinSelectionFragment();
                        break;
                    case "Thanh toán thẻ tín dụng":
                        currentFragment = new BaseFragment();
                        break;
                    case "Nạp tiền điện thoại":
                        currentFragment = new PinSelectionFragment();
                        break;
                    case "Thanh toán bằng Mobile POS":
                        currentFragment = new BaseFragment();
                        break;
                }
                break;
            default:
                currentFragment = new BaseFragment();
                break;
        }

        mCurrentSectionID = sectionId;
        mCurrentTitle = title;
        processPushFragment(currentFragment);
    }

    private void logout() {
        DialogFactory.createMessageDialogLogout(this, getResources().getString(R.string.confirm_logout),
                new DialogFactory.DialogListener.LogoutListener() {
                    @Override
                    public void logOut() {
                        clearDataLocal();
                        updateSlideMenu(userIsLogined());
                        goLoginPage();
                        updateView();
                    }
                });
    }

    private void attachExpandableListView() {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.navigation_expandablelistview, null);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        mSlideMenuList = new SideMenuDataFactory(this, userIsLogined()).makeSection();
        adapter = new NavigationDrawerAdapter(this, mSlideMenuList);
        adapter.setItemClickListener(mChildItemClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mSlidingMenu.setMenu(layout);
    }

    public void updateSlideMenu(boolean isLogined) {
        attachExpandableListView();
    }

    private void processPushFragment(BaseFragment fragment) {
        if (fragment == null) return;
        fragment.setBaseListener(this);
        if (footerMenu != null) {
            if (fragment instanceof LoginFragment) {
                footerMenu.setVisibility(View.GONE);
            } else {
                footerMenu.setVisibility(View.VISIBLE);
            }
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.acitivity_in_from_right_to_left, R.anim.activity_out_from_left);
        fragmentTransaction.replace(R.id.main_content, fragment);
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(SuggestionObject suggestionObject) {
        onNextDetailArticle(suggestionObject);
    }

    @Override
    public void updateNavigationBar(String fragmentClass, String title) {

        changeIconHome(fragmentClass.equalsIgnoreCase("HomeFragment") ? R.drawable.search : R.drawable.ic_home);
        showHideBack(!fragmentClass.equalsIgnoreCase("SearchFragment") && !fragmentClass.equalsIgnoreCase("HomeFragment") && !fragmentClass.equalsIgnoreCase("LoginFragment"), title);

        if (isSearchOpening && !fragmentClass.equalsIgnoreCase("SearchFragment"))
            hideSearchBar();
        else if (!isSearchOpening && fragmentClass.equalsIgnoreCase("SearchFragment"))
            showSearchBar();

    }

    @Override
    public void goToPolicyPage() {
        currentFragment = new PolicyFragment();
        processPushFragmentDKDK(currentFragment);
    }

    private void processPushFragmentDKDK(BaseFragment fragment) {
        if (fragment == null) return;
        fragment.setBaseListener(this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.acitivity_in_from_right_to_left, R.anim.activity_out_from_left);
        fragmentTransaction.replace(R.id.main_content, fragment);
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchItemClick(SuggestionObject suggestionObject) {
        onNextDetailArticle(suggestionObject);
    }

}
