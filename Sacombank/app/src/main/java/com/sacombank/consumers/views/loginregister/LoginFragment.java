package com.sacombank.consumers.views.loginregister;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.google.gson.Gson;
import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.presenter.loginregister.register.AuthenticationCodeImpl;
import com.sacombank.consumers.presenter.loginregister.register.AuthenticationCodePresenter;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.loginregister.login.SignInFragment;
import com.sacombank.consumers.views.loginregister.register.AuthenticationCodeVIew;
import com.sacombank.consumers.views.loginregister.register.SignUpFragment;
import com.sacombank.consumers.views.loginregister.resetpassword.ResetPasswordFragment;
import com.sacombank.consumers.views.widgets.ButtonPlus;
import com.sacombank.consumers.views.widgets.EditTextViewPlus;
import com.sacombank.consumers.views.widgets.TextViewPlus;
import com.sacombank.consumers.views.widgets.dialog.DialogFactory;
import com.stb.api.bo.consumer.ConsumerAuthCodeVerification;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends BaseFragment implements SignInFragment.SignInListener, LoginListener,
        View.OnClickListener, AuthenticationCodeVIew {


    private static final String TAG = LoginFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private CoordinatorLayout layout1;
    private LinearLayout layout2;
    private int currentPage;
    private OnLoginFragmentInteractionListener mListener;
    private ButtonPlus btnFinish;
    private EditTextViewPlus edtConfirmCode;
    private AuthenticationCodePresenter mAuthenticationPresenter;
    private ScrollView scrollviewFragment;
    private FrameLayout layout_Outside;
    private TextViewPlus tvMobile;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthenticationPresenter = new AuthenticationCodeImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout1 = (CoordinatorLayout) view.findViewById(R.id.layout_sign_in);
        tvMobile = (TextViewPlus) view.findViewById(R.id.tvMobile);
        tvMobile.setText(String.format(getResources().getString(R.string.signin_confirm_message), ApiManager.getMobileNo()));
        layout2 = (LinearLayout) view.findViewById(R.id.layout_confirm);
        btnFinish = (ButtonPlus) view.findViewById(R.id.btnFinishSignIn);
        edtConfirmCode = (EditTextViewPlus) view.findViewById(R.id.edtConfirmCode);
        scrollviewFragment= (ScrollView) view.findViewById(R.id.scrollviewFragment);
        layout_Outside= (FrameLayout) view.findViewById(R.id.layout_Outside);
        btnFinish.setOnClickListener(this);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
        addEventScrollView();
    }

    private void addEventScrollView() {
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                viewPager.getWindowVisibleDisplayFrame(r);
                if (viewPager.getRootView().getHeight() - (r.bottom - r.top) > 500) { // if more than 100 pixels, its probably a keyboard...
                    scrollviewFragment.setLayoutParams(new FrameLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,viewPager.getRootView().getHeight()/2));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            Gravity.TOP);
                    layout_Outside.setLayoutParams(params);
                } else {
                    scrollviewFragment.setLayoutParams(new FrameLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            Gravity.BOTTOM);
                    layout_Outside.setLayoutParams(params);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(LoginFragment.class.getSimpleName(), "");
    }
    SignUpFragment signUpFragment;
    SignInFragment signInFragment;
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        signInFragment = new SignInFragment();
        signInFragment.setListener(this);
        signUpFragment = new SignUpFragment();
        signUpFragment.setListener(this);
        adapter.addFragment(signInFragment, getString(R.string.login_title));
        adapter.addFragment(signUpFragment, getString(R.string.register));
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        resetPasswordFragment.setListener(this);
        adapter.addFragment(resetPasswordFragment, getString(R.string.forgot_pass_title));
        viewPager.setAdapter(adapter);
    }


    @Override
    public void showLoading() {
        showLoadingPage();
    }

    @Override
    public void hideLoading() {
        hideLoadingPage();
    }

    @Override
    public void onLoginSuccess(Object user) {

        goHomePage();
    }

    private void goHomePage() {
        ((MainActivity) getActivity()).onBackHomePage();
    }

    @Override
    public void onSkip() {
        goHomePage();
    }

    @Override
    public void gotoSignIn() {
        gotoTab(0);
    }

    @Override
    public void gotoSignUp() {
        gotoTab(1);
    }

    @Override
    public void denTrangDieuKhoan() {
        mListener.goToPolicyPage();
    }

    @Override
    public void nextSignUpConfirmInputPage() {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
    }

    public void nextSignUp2Page() {
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
    }

    public void gotoTab(int pos) {
        TabLayout.Tab tab = tabLayout.getTabAt(pos);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFinishSignIn:
                if (mAuthenticationPresenter != null) {
                    mAuthenticationPresenter.authenticate(edtConfirmCode.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onError(Object error) {
        Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
        if (error != null) {
            final String errorMsg = ((ConsumerAuthCodeVerification) error).Description;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                    DialogFactory.createMessageDialog(getActivity(), errorMsg);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingPage();
                }
            });
        }
    }

    @Override
    public void onSuccess(Object data) {
        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(data));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingPage();
                nextSignUp2Page();
                signUpFragment.onNextPage2();
            }
        });
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
