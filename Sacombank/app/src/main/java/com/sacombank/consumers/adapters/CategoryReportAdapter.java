package com.sacombank.consumers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sacombank.consumers.R;
import com.sacombank.consumers.utils.DateUtil;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.util.List;

/**
 * Created by DUY on 7/14/2017.
 */

public class CategoryReportAdapter extends RecyclerView.Adapter<CategoryReportAdapter.CategoryReportViewHolder> {

    private static final String SCB_DATE_FORMAT = "yyyyMMdd";
    private List<SuggestionObject> suggestionList;
    private Context mContext;
    private OnItemClickListener listener;

    public void updateData(List<SuggestionObject> suggestionList) {
        this.suggestionList = suggestionList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class CategoryReportViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryReportTitle;
        private TextView tvCategoryReportDesc;
        private TextView tvCategoryReport;
        private TextView tvHightLight;
        private TextView tvDueDate;
        private ImageView imgCategoryReport;
        private LinearLayout layoutCategoryReport;
        private RatingBar ratingBarCategoryReport;


        private CategoryReportViewHolder(View view) {
            super(view);
            tvCategoryReportTitle = (TextView) view.findViewById(R.id.tvCategoryReportTitle);
            tvCategoryReportDesc = (TextView) view.findViewById(R.id.tvCategoryReportDescription);
            ratingBarCategoryReport = (RatingBar) view.findViewById(R.id.ratingBarCategoryReport);
            imgCategoryReport = (ImageView) view.findViewById(R.id.imgCategoryReport);
            layoutCategoryReport = (LinearLayout) view.findViewById(R.id.layoutCategoryReport);
            tvCategoryReport = (TextView) view.findViewById(R.id.tvCategoryReport);
            tvHightLight = (TextView) view.findViewById(R.id.tvHightLight);
            tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);

            LayerDrawable stars = (LayerDrawable) ratingBarCategoryReport.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("#FFCC00"), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public CategoryReportAdapter(Context context, List<SuggestionObject> suggestionList, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.suggestionList = suggestionList;
    }


    @Override
    public CategoryReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_report, parent, false);

        return new CategoryReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryReportViewHolder holder, final int position) {
        holder.tvCategoryReportTitle.setText(Html.fromHtml(suggestionList.get(position).Title));
        holder.tvCategoryReportDesc.setText(Html.fromHtml(suggestionList.get(position).SuggestionSummary));
        float rating = (suggestionList.get(position).Rating.equals("") ? 0 : Float.parseFloat(suggestionList.get(position).Rating));
        holder.ratingBarCategoryReport.setRating(rating);
        holder.tvCategoryReport.setText(String.format(mContext.getResources().getString(R.string.like_counter),
                suggestionList.get(position).LikeCounter));

        holder.tvHightLight.setText(suggestionList.get(position).TitleHighLight);
        long remainDays = DateUtil.getDayBetweenTwoDate(suggestionList.get(position).StartDate, suggestionList.get(position).EndDate, SCB_DATE_FORMAT);
        holder.tvDueDate.setText(String.format(mContext.getResources().getString(R.string.due_date_promotion),
                String.valueOf(remainDays)));

        Glide.with(mContext)
                .load(suggestionList.get(position).SuggestionImage)
                .into(holder.imgCategoryReport);

        holder.layoutCategoryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.suggestionList.size();
    }


}