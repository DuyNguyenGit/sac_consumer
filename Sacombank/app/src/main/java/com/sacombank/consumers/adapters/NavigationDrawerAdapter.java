package com.sacombank.consumers.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacombank.consumers.R;
import com.sacombank.consumers.models.sidemenu.Section;
import com.sacombank.consumers.models.sidemenu.SectionItem;
import com.sacombank.consumers.views.viewholder.SectionItemViewHolder;
import com.sacombank.consumers.views.viewholder.SectionViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


/**
 * Created by DUY on 7/12/2017.
 */
// expandable recycler view : https://github.com/thoughtbot/expandable-recycler-view
public class NavigationDrawerAdapter extends ExpandableRecyclerViewAdapter<SectionViewHolder, SectionItemViewHolder> {

    Context context;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    View prevView;

    public NavigationDrawerAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }


    @Override
    public SectionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slidingmenu_sectionview, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public SectionItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slidingmenu_sectionitem, parent, false);
        return new SectionItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final SectionItemViewHolder holder, final int flatPosition,
                                      final ExpandableGroup group, final int childIndex) {

        final SectionItem sectionItem = ((Section) group).getItems().get(childIndex);
        holder.setArtistName(sectionItem.getName());
        holder.setEnable(sectionItem.isLogined());
        holder.childTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChildItemClick(group, group.getItems().get(childIndex));
            }
        });

    }

    @Override
    public ExpandableGroup getGroup(int position) {
        return super.getGroup(position);
    }

    @Override
    public void onBindGroupViewHolder(final SectionViewHolder holder, final int flatPosition,
                                      final ExpandableGroup group) {

        holder.setSectionTitle(group);
        if (group.getItemCount() == 0) holder.arrow.setVisibility(View.GONE);
        else holder.arrow.setVisibility(View.VISIBLE);
        holder.setEnable(group);
        holder.genreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prevView != null) {
                    prevView.setSelected(false);
                }

                view.setSelected(true);
                prevView = view;
                if (group.getItemCount() != 0)
                    toggleGroup(group);
                else
                    mListener.onGroupItemClick(group);
            }
        });
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onChildItemClick(Object section, Object item);

        void onGroupItemClick(Object section);
    }
}
