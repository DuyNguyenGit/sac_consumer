package com.sacombank.consumers.views.cardmanagement;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.adapters.HorizontalPagerAdapter;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.L2.CardObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardManagerFragment extends BaseFragment {


    private static final String TAG = CardManagerFragment.class.getSimpleName();
    private static final int INFO = 0;
    private static final int SAOKE = 1;
    private static final int BALANCE = 2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int currentPage;
    private HorizontalPagerAdapter pagerAdapter;
    private int[] imageResIdUnselected = {R.drawable.ic_card_tab_info,
            R.drawable.ic_card_tab_copy,
            R.drawable.ic_card_tab_balance};
    private int[] imageResIdSelected = {R.drawable.ic_card_tab_info_selected,
            R.drawable.ic_card_tab_copy_selected,
            R.drawable.ic_card_tab_balance_selected};

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private BalanceFragment balanceFragment;
    private SaoKeFragment saoKeFragment;
    private CommonInfoFragment infoFragment;
    private List<CardObject> cardObjectList;
    private CardBaseFragment currentFragment;
    private CardObject currentCardObject;


    public CardManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(CardManagerFragment.class.getSimpleName(), "Quản lý thẻ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_manager, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardObject[] cardObjects = ApiManager.getCardObjectList();

        Log.e(TAG, "onViewCreated: >>> cardObjects = " + new Gson().toJson(cardObjects));
        cardObjectList = Arrays.asList(cardObjects!=null ? cardObjects : new CardObject[]{});
        if(cardObjectList.size()>0 && cardObjectList.get(0)!=null){
            currentCardObject=cardObjectList.get(0);
            Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(currentCardObject));
            //ApiManager.testConsumerCardBalanceInquiry();
        }
        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        final HorizontalInfiniteCycleViewPager viewPager = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        pagerAdapter = new HorizontalPagerAdapter(getContext(), false, cardObjectList);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(cardPageChangeListener);
        setPageViewIndicator();
        if(cardObjectList.size()>0){
            viewPager.post(new Runnable(){
                @Override
                public void run() {
                    cardPageChangeListener.onPageSelected(0);
                }
            });
        }
        setupViewPager(view);
    }

    ViewPager.OnPageChangeListener cardPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int currentPosition=position%cardObjectList.size();
            Log.e(TAG, "onPageSelected: UP_VIEW_PAGER >>>" + currentPosition);
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }
            dots[currentPosition].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            currentCardObject = cardObjectList.get(currentPosition)!=null ? cardObjectList.get(currentPosition) : new CardObject();

            if (currentFragment != null) {
                currentFragment.updateUI(currentCardObject);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setPageViewIndicator() {

        dotsCount = pagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    viewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            pager_indicator.addView(dots[i], params);
        }
        if(dots!=null && dots.length>0){
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
    }

    private void setupViewPager(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        setupTabs(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.imgTab);
                TextView tvTitle = (TextView)tab.getCustomView().findViewById(R.id.tvTabTitle);
                imageView.setImageResource(imageResIdSelected[tab.getPosition()]);
                tvTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.title_tabbar_selected_account_manager, null));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.imgTab);
                TextView tvTitle = (TextView)tab.getCustomView().findViewById(R.id.tvTabTitle);
                imageView.setImageResource(imageResIdUnselected[tab.getPosition()]);
                tvTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.title_tabbar_unselected_account_manager, null));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        customTab(0);

        viewPager.post(new Runnable(){
            @Override
            public void run() {
                onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        });
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            Log.e(TAG, "onPageSelected: >>>" + position);
            updateTabFragment(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void updateTabFragment(int position) {
        currentFragment = adapter.getItem(position);
        currentFragment.updateUI(currentCardObject);
    }


    private void customTab(int selectedPos) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i, i==selectedPos));
        }
    }


    private void setupTabs(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager(), getActivity());

        infoFragment = new CommonInfoFragment();
        saoKeFragment = new SaoKeFragment();
        balanceFragment = new BalanceFragment();

        adapter.addFragment(infoFragment, getString(R.string.tab_title_card_info));
        adapter.addFragment(saoKeFragment, getString(R.string.tab_title_card_copy));
        adapter.addFragment(balanceFragment, getString(R.string.tab_title_card_balance));
        viewPager.setAdapter(adapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<CardBaseFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Context context;


        public ViewPagerAdapter(FragmentManager manager, Context context) {
            super(manager);
            this.context = context;
        }

        @Override
        public CardBaseFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(CardBaseFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public View getTabView(int position, boolean isSelected) {
            View v = LayoutInflater.from(this.context).inflate(R.layout.tab_account_custom, null);
            TextView tv = (TextView) v.findViewById(R.id.tvTabTitle);
            ImageView img = (ImageView) v.findViewById(R.id.imgTab);
            tv.setText(mFragmentTitleList.get(position));
            if (isSelected) {
                img.setImageResource(imageResIdSelected[position]);
                tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.title_tabbar_selected_account_manager, null));
            } else {
                img.setImageResource(imageResIdUnselected[position]);
                tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.title_tabbar_unselected_account_manager, null));
            }
            return v;
        }
    }
}
