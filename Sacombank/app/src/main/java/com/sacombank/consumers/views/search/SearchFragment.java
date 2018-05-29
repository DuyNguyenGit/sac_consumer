package com.sacombank.consumers.views.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacombank.consumers.R;
import com.sacombank.consumers.adapters.CategoryReportAdapter;
import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.L2.SuggestionObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment {

    CategoryReportAdapter adapter;
    RecyclerView rcvResult;
    SearchPageListener listener;

    public void setListener(SearchPageListener listener) {
        this.listener = listener;
    }

    public interface SearchPageListener {
        void onSearchItemClick(SuggestionObject suggestionObject);
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    private CategoryReportAdapter.OnItemClickListener mOnItemClickListener =
            new CategoryReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int vitri) {
                    listener.onSearchItemClick(new SuggestionObject());//null Search
                }
            };

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(SearchFragment.class.getSimpleName(), "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
    }

    private void setUpViews(View view) {
        // Vertical List View for Category Reports
        rcvResult = (RecyclerView) view.findViewById(R.id.rcvSearch);
        adapter = new CategoryReportAdapter(getActivity(), new ArrayList<SuggestionObject>(), mOnItemClickListener);
        LinearLayoutManager verticalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvResult.setLayoutManager(verticalLayoutManagaer);
        rcvResult.setAdapter(adapter);
    }

}
