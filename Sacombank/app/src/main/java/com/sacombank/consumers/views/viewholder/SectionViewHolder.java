package com.sacombank.consumers.views.viewholder;

import android.graphics.Color;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sacombank.consumers.R;
import com.sacombank.consumers.models.sidemenu.Section;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by DUY on 7/16/2017.
 */

public class SectionViewHolder extends GroupViewHolder {

    public TextView genreName;
    public ImageView arrow;
    private ImageView icon;
    public RelativeLayout genreLayout;

    public static final String DEACTIVE_COLOR = "#FF666E79";
    public static final String ACTIVE_COLOR = "#FFFFFFFF";

    public SectionViewHolder(View itemView) {
        super(itemView);
        genreName = (TextView) itemView.findViewById(R.id.list_item_genre_name);
        arrow = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
        icon = (ImageView) itemView.findViewById(R.id.list_item_genre_icon);
        genreLayout = (RelativeLayout) itemView.findViewById(R.id.group_layout);
    }

    @Override
    public int getGroupPosition() {
        return 0;
    }

    public void setSectionTitle(ExpandableGroup section) {
        if (section instanceof Section) {
            genreName.setText(section.getTitle());
            icon.setBackgroundResource(((Section) section).getIconResId());
            if (section.getItemCount() == 0)
                arrow.setVisibility(View.GONE);
            else
                arrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    public void setEnable(ExpandableGroup group) {
        if (group instanceof Section) {
            genreName.setTextColor(Color.parseColor(((Section) group).isLogined() ? ACTIVE_COLOR : DEACTIVE_COLOR));
            genreLayout.setEnabled(((Section) group).isLogined());
        }
    }
}
