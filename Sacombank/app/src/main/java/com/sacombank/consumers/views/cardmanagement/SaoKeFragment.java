package com.sacombank.consumers.views.cardmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sacombank.consumers.R;
import com.sacombank.consumers.utils.DateUtil;
import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.L2.CardObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TranVietThuc on 13/08/2017.
 */

public class SaoKeFragment extends CardBaseFragment {
    private static final String TAG = SaoKeFragment.class.getSimpleName();
    private ListView lvSaoKe;
    private CustomListviewSaoKeAdapter saoKeAdapter;
    private List<ItemSaoKe> saoKeList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saoke, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControllers(view);
        addEvents(view);


    }

    private void addEvents(View view) {

    }

    private void addControllers(View view) {
        lvSaoKe = (ListView) view.findViewById(R.id.lvSaoKe);
        saoKeList = new ArrayList<>();
        saoKeAdapter = new CustomListviewSaoKeAdapter(getActivity(), R.layout.item_saoke, saoKeList);
        lvSaoKe.setAdapter(saoKeAdapter);
    }

    @Override
    protected void updateUI(CardObject cardObject) {
        Log.e(TAG, "updateUI: >>>" + new Gson().toJson(cardObject));
        if (saoKeList != null) {
            saoKeList.clear();
            if (cardObject.BillDateList != null) {
                for (String key : cardObject.BillDateList.keySet()) {
                    saoKeList.add(new ItemSaoKe(key, cardObject.BillDateList.get(key)));
                }
            }
        }

        saoKeAdapter.notifyDataSetChanged();
    }

    public class CustomListviewSaoKeAdapter extends ArrayAdapter<ItemSaoKe> {
        @NonNull
        Context context;
        @LayoutRes
        int resource;
        @NonNull
        List<ItemSaoKe> objects;

        public CustomListviewSaoKeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItemSaoKe> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.objects = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.item_saoke, null);
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.ln_saoke);
            TextView txt_ma_saoke = (TextView) view.findViewById(R.id.txt_ma_saoke);
            TextView txt_ngay_saoke = (TextView) view.findViewById(R.id.txt_ngay_saoke);
            txt_ma_saoke.setText(objects.get(position).getCode());
            txt_ngay_saoke.setText(Html.fromHtml("<u>" + objects.get(position).getDate() + "</u>"));
            if (position % 2 == 0) {
                view.setBackgroundResource(R.color.color_bg_introduce);
            } else {
                view.setBackgroundResource(R.color.white);
            }
            return view;
        }
    }

    public class ItemSaoKe {
        private String code;
        private String date;

        public ItemSaoKe(String code, String date) {
            this.code = code;
            this.date = date;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
