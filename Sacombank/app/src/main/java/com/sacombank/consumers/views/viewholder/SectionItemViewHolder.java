package com.sacombank.consumers.views.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by DUY on 7/16/2017.
 */

public class SectionItemViewHolder extends ChildViewHolder {

    public TextView childTextView;
    public static final String DEACTIVE_COLOR = "#FF666E79";
    public static final String ACTIVE_COLOR = "#FFFFFFFF";

    public SectionItemViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    }

    public void setArtistName(String name) {
        childTextView.setText(name);
    }

    public void setEnable(boolean logined) {
        childTextView.setTextColor(Color.parseColor(logined ? ACTIVE_COLOR : DEACTIVE_COLOR));
        childTextView.setEnabled(logined);
    }
}