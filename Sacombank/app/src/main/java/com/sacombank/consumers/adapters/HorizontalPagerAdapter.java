package com.sacombank.consumers.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sacombank.consumers.R;
import com.sacombank.consumers.utils.Utils;
import com.stb.api.bo.consumer.L2.CardObject;

import java.util.List;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    private int currentPosition=0;
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.image_card_1,
                    "Strategy"
            ),
            new Utils.LibraryObject(
                    R.drawable.image_card_2,
                    "Design"
            ),
            new Utils.LibraryObject(
                    R.drawable.image_card_3,
                    "Development"
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CardObject> cardObjectList;

    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay, List<CardObject> cardObjectList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
        this.cardObjectList = cardObjectList;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? cardObjectList.size() : cardObjectList.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        Glide.with(mContext)
                .load(cardObjectList.get(position).CardImageUrl)
                .into(img);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

}
