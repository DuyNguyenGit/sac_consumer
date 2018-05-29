package com.sacombank.consumers.views.home.category;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacombank.consumers.R;
import com.sacombank.consumers.adapters.CategoryReportAdapter;
import com.sacombank.consumers.views.home.HomeFragment;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    CategoryReportAdapter categoryReportAdapter;
    RecyclerView rcvCategoryReport;
    private CategoryReportAdapter.OnItemClickListener mOnItemClickListener = 
            new CategoryReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int vitri) {
                    listener.onFragmentInteraction(suggestionObjectList.get(vitri));
                }
            };
    private HomeFragment.OnFragmentInteractionListener listener;
    private int position;
    List<SuggestionObject> suggestionObjectList;

    public CategoryFragment() {
        
    }

    public static CategoryFragment newInstance(int position, List<SuggestionObject> suggestionObjectList) {
        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("listdata", (Serializable) suggestionObjectList);
        categoryFragment.setArguments(args);

        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);
        suggestionObjectList = (List<SuggestionObject>) getArguments().getSerializable("listdata");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
    }

    private void setUpViews(View view) {
        // Vertical List View for Category Reports
        rcvCategoryReport = (RecyclerView) view.findViewById(R.id.vertical_recycler_view);
        categoryReportAdapter = new CategoryReportAdapter(getActivity(), suggestionObjectList, mOnItemClickListener);
        LinearLayoutManager verticalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvCategoryReport.setLayoutManager(verticalLayoutManagaer);
        rcvCategoryReport.setAdapter(categoryReportAdapter);
    }


    public void setListener(HomeFragment.OnFragmentInteractionListener mListener) {
        this.listener = mListener;
    }

    public void setData(List<SuggestionObject> suggestionList) {
        categoryReportAdapter.updateData(suggestionList);
    }
}
