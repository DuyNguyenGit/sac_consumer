package com.sacombank.consumers.views.home.category;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.L2.SuggestionObject;

public class ArticleDetailFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView img;
    private TextView txtContentDetail;
    private SuggestionObject suggestionObject = null;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(String param1, String param2) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suggestionObject = (SuggestionObject) getActivity().getIntent().getSerializableExtra("DetailArticle");
        Log.e("KetQua:",new Gson().toJson(suggestionObject));
        addControllers();
    }

    private void addControllers() {
        img = (ImageView) getView().findViewById(R.id.img);
        txtContentDetail = (TextView) getView().findViewById(R.id.txtContentDetail);
        if(suggestionObject!=null){
            Glide.with(getContext())
                    .load(suggestionObject.SuggestionImage)
                    .into(img);
            txtContentDetail.setText(Html.fromHtml(suggestionObject.SuggestionSummary));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(ArticleDetailFragment.class.getSimpleName(), getResources().getString(R.string.title_article_detail));

    }
}
