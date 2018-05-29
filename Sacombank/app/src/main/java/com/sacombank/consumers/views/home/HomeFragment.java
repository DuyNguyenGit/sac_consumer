package com.sacombank.consumers.views.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ihsanbal.introview.AdapterPageSection;
import com.ihsanbal.introview.IntroView;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.staticmodels.HomeFeatureModel;
import com.sacombank.consumers.presenter.home.HomeImpl;
import com.sacombank.consumers.presenter.home.HomePresenter;
import com.sacombank.consumers.utils.DummyDataUtil;
import com.sacombank.consumers.views.BaseFragment;
import com.sacombank.consumers.views.home.category.CategoryFragment;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.ConsumerPublicHomeInquiry;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.bo.consumer.L2.CategoryObject;
import com.stb.api.bo.consumer.L2.FeatureObject;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends BaseFragment implements HomeView {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    IntroView mIntroView;
    CircleIndicator mCircleIndicator;
    private HomePresenter homePresenter;
    List<CategoryObject> VIList = new ArrayList<>();
    List<CategoryObject> ENList = new ArrayList<>();
    List<SuggestionObject> suggestionList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private int[] imageResId = {R.drawable.ic_home_newest,
            R.drawable.ic_home_food,
            R.drawable.ic_home_travel,
            R.drawable.ic_home_shopping,
            R.drawable.ic_online,
            R.drawable.ic_online,
            R.drawable.ic_online,
            R.drawable.ic_online,
            R.drawable.ic_online,
            R.drawable.ic_online};

    @Override
    public void showLoading() {
        showLoadingPage();
    }

    @Override
    public void hideLoading() {
        hideLoadingPage();
    }

    @Override
    public void onError(Object error) {
        Log.e(TAG, "onError: >>>");
    }

    @Override
    public void onSuccess(Object result) {

        // TODO : Dummy data for testing
//        String json = DummyDataUtil.getDummyDataFromAssets(getActivity(), "test_home.json");
//        ConsumerPublicHomeInquiry data = new Gson().fromJson(json, ConsumerPublicHomeInquiry.class);
        ConsumerPublicHomeInquiry data = (ConsumerPublicHomeInquiry) result;

        Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(result));
        if (data != null) {
            ConsumerPublicHomeInquiry homeData = data;
            VIList.clear();
            ENList.clear();
            for (CategoryObject object : homeData.CategoryObject) {
                if (object.LanguageID.equalsIgnoreCase("VI")) {
                    VIList.add(object);
                } else {
                    ENList.add(object);
                }
            }

            Collections.sort(VIList);
            Collections.sort(ENList);

            setupData(homeData);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(SuggestionObject suggestionObject);
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (homePresenter == null) {
            homePresenter = new HomeImpl(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(HomeFragment.class.getSimpleName(), "");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(view);
        setupSlideImage(view);
        loadData();
    }

    private void loadData() {
        homePresenter.loadHomeData();
    }

    public void gotoTab(int pos) {
        TabLayout.Tab tab = tabLayout.getTabAt(pos);
        if (tab != null) {
            tab.select();
        }
    }

    private void setupViewPager(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected: >>>" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupSlideImage(View view) {
        mIntroView = (IntroView) view.findViewById(R.id.intro_view);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        mIntroView.setOffscreenPageLimit(3);
    }

    private void customTab() {
        if (tabLayout.getTabCount() <= 4) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }

    private void setupData(final ConsumerPublicHomeInquiry homeData) {

        if (ApiManager.getSuggestObjectList() != null) {
            suggestionList = ApiManager.getSuggestObjectList().length > 0 ? Arrays.asList(ApiManager.getSuggestObjectList()) : Arrays.asList(homeData.SuggestionObject);
        } else {
            suggestionList = Arrays.asList(homeData.SuggestionObject);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ViewPagerAdapter(getChildFragmentManager(), getActivity(), VIList);
                for (int i = 0; i < VIList.size(); i++) {
                    List<SuggestionObject> list = new ArrayList<>();
                    for (SuggestionObject suggestionObject : suggestionList) {
                        if (suggestionObject.CategoryIDList != null) {
                            List<Integer> categoryId = Arrays.asList(suggestionObject.CategoryIDList);
                            if (categoryId.contains(VIList.get(i).CategoryID)) {
                                list.add(suggestionObject);
                            }
                        }
                    }
                    CategoryFragment fragment = CategoryFragment.newInstance(i, list);
                    fragment.setListener(mListener);
                    adapter.addFragment(fragment, VIList.get(i).Name);
                }
                viewPager.setAdapter(adapter);
                customTab();

                loadFeatureData(homeData);
            }
        });
    }

    private void loadFeatureData(ConsumerPublicHomeInquiry homeData) {
        final List<FeatureObject> featureList = Arrays.asList(homeData.FeatureObject);

        mIntroView.init(getChildFragmentManager(), featureList,
                new AdapterPageSection.OnItemClickListener() {

                    @Override
                    public void onItemClick(int position) {
                        Log.e(TAG, "onItemClick: >>>" + new Gson().toJson(featureList.get(position)));
                        //mListener.onFragmentInteraction();
                    }

                    @Override
                    public void onItemSelect(int position) {

                    }
                });
        mCircleIndicator.setViewPager(mIntroView);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Context context;
        private List<CategoryObject> VIList;

        public ViewPagerAdapter(FragmentManager manager, Context context, List<CategoryObject> VIList) {
            super(manager);
            this.context = context;
            this.VIList = VIList;
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

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(this.context).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.tvTabTitle);
            tv.setText(mFragmentTitleList.get(position));
            ImageView img = (ImageView) v.findViewById(R.id.imgTab);
            Glide.with(this.context)
                    .load(VIList.get(position).InactiveIconUrl)
                    .error(R.drawable.ic_online)
                    .into(img);
            return v;
        }
    }
}
