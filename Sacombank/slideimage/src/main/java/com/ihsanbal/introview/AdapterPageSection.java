package com.ihsanbal.introview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sacombank.consumers.models.staticmodels.HomeFeatureModel;
import com.stb.api.bo.consumer.L2.FeatureObject;

import java.util.List;

/**
 * @author ihsan on 10/10/16.
 */

public class AdapterPageSection extends FragmentPagerAdapter {

    private final Bundle bundle;
    private final Context context;
    private final OnItemClickListener onItemClickListener;
    List<FeatureObject> featureList;

    public interface OnItemClickListener {

        void onItemClick(int position);

        void onItemSelect(int position);

    }

    AdapterPageSection(Context context, FragmentManager fm, Bundle bundle,
                       List<FeatureObject> featureList,
                       OnItemClickListener onItemClickListener) {
        super(fm);
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.featureList = featureList;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentPage.newInstance(this.featureList.get(position).FeatureImage, bundle, onItemClickListener, position);
    }

    @Override
    public int getCount() {
        return featureList != null ? featureList.size() : 0;
    }
}
