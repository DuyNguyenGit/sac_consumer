package com.sacombank.consumers.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sacombank.consumers.R;
import com.sacombank.consumers.models.home.Category;

import java.util.List;

/**
 * Created by DUY on 7/14/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory;
        private ImageView imgCategory;
        private LinearLayout layoutCategory;

        private MyViewHolder(View view) {
            super(view);
            tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            imgCategory = (ImageView) view.findViewById(R.id.imgCategory);
            layoutCategory = (LinearLayout) view.findViewById(R.id.layoutCategory);

        }
    }


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.mContext = context;
        this.categoryList = categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvCategory.setText(categoryList.get(position).getTitle());
        Glide.with(mContext)
                .load(categoryList.get(position).getImageUrl())
                .into(holder.imgCategory);

        holder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.tvCategory.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}