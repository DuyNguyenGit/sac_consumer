package com.sacombank.consumers.views.history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.michael.easydialog.EasyDialog;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.models.jsonobjects.HistoryData;
import com.sacombank.consumers.presenter.history.HistoryImpl;
import com.sacombank.consumers.presenter.history.HistoryPresenter;
import com.sacombank.consumers.presenter.home.HomeImpl;
import com.sacombank.consumers.utils.DateUtil;
import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.ConsumerCardMiniStatement;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.bo.consumer.L2.TransactionObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by TranVietThuc on 11/08/2017.
 */

public class HistoryFragment extends BaseFragment implements HistoryView {
    private static final String TAG = HistoryFragment.class.getSimpleName();
    private ListView lv_tooltip;
    private CustomListviewTooltipAdapter adapterTooltip;
    private TextView txtTitleHistory;
    private CustomListviewAdapter listviewAdapter;
    private ListView lvHistory;
    private SimpleDateFormat sdf;

    private EasyDialog dialog;
    private int mTooltipPosition = 0;
    private HistoryPresenter historyPresenter;
    private ConsumerAccountLogin userData;
    private List<CardObject> cardList;

    public HistoryFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommonUI(HistoryFragment.class.getSimpleName(), "Lịch sử giao dịch");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (historyPresenter == null) {
            historyPresenter = new HistoryImpl(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        addEvents(view);
    }

    private void initView(View view) {
        txtTitleHistory = (TextView) view.findViewById(R.id.txtTitleHistory);
        lvHistory = (ListView) view.findViewById(R.id.lvHistory);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        listviewAdapter = new CustomListviewAdapter(getActivity());
        lvHistory.setAdapter(listviewAdapter);

    }

    private void initData() {
        dummyTooltipData();
    }

    private void dummyTooltipData() {
        CardObject[] cardArray = ApiManager.getCardObjectList();
//            CardObject x=new CardObject();
//            x.AvailableBalance=null;
//            x.CardCategory="D";
//            x.CardIssuer="STB";
//            x.CardNumber="977777xxxxxx0000";
//            x.CardStatus="";
//            x.CardToken="3906751445";
//            x.CardType="B";
//            x.CurrencyCode="704";
//            x.CurrentLimit=null;
//            x.DefaultIndicator="true";
//            x.ExpiryDate=null;
//            x.IsChecked=false;
//            x.LimitedBalance=null;
//            x.LinkedIndicator=null;
        cardList = cardArray != null ? Arrays.asList(cardArray) : new ArrayList<CardObject>();
//            cardList.add(x);
        if (cardList.size() > 0) {
            txtTitleHistory.setText(cardList.get(0).CardNumber);
            loadHistoryData(cardList.get(0).CardToken);
        }
    }

    private void loadHistoryData(String cardToken) {
        if (historyPresenter != null) {
            Log.e(TAG, "loadHistoryData: >>>" + cardToken.toString());
            HistoryData data = new HistoryData(cardToken);
            historyPresenter.loadHistoryData(data);
        }
    }

    private void addEvents(View view) {
        txtTitleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                txtTitleHistory.getLocationInWindow(location);
                location[0] += 120;
                location[1] += txtTitleHistory.getHeight() - 35;
                View tooltipView = getActivity().getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
                dialog = new EasyDialog(getActivity())
                        .setLayout(tooltipView)
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.white))
                        .setLocation(location)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setAnimationAlphaShow(600, 0.0f, 1.0f)
                        .setAnimationAlphaDismiss(600, 1.0f, 0.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(true)
                        .setMarginLeftAndRight(40, 40)
                        .setOutsideColor(getActivity().getResources().getColor(R.color.outside_color_trans));
                dialog.show();
                lv_tooltip = (ListView) tooltipView.findViewById(R.id.lv_tooltip);

                setUpToolTipAdapter();

                lv_tooltip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtTitleHistory.setText(cardList.get(position).CardNumber);
                        mTooltipPosition = position;
                        loadHistoryData(cardList.get(position).CardToken);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void setUpToolTipAdapter() {
        if (cardList != null) {
            for (int i = 0; i < cardList.size(); i++) {
                cardList.get(i).IsChecked = (i == mTooltipPosition);
            }
            adapterTooltip = new CustomListviewTooltipAdapter(getActivity(), R.layout.item_tooltip, cardList);
            lv_tooltip.setAdapter(adapterTooltip);
        }
    }

    @Override
    public void showLoading() {
        showLoadingPage();
    }

    @Override
    public void hideLoading() {
        hideLoadingPage();
    }

    @Override
    public void onError(Object error) {

    }

    @Override
    public void onSuccess(Object data) {
        ConsumerCardMiniStatement historyData = ((ConsumerCardMiniStatement) data);
        Log.e(TAG, "onSuccess: >>>>>>" + new Gson().toJson(data));
        Log.e(TAG, "onSuccess: >>>>>>" + DateUtil.getDateHistory(historyData.TransactionObject[0].TransactionDate));

        List<TransactionObject> resultList = Arrays.asList(historyData.TransactionObject);

        List<String> dateList = new ArrayList<>();
        final List<TransactionObject> transactionList = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            if (!dateList.contains(resultList.get(i).TransactionDate)) {
                dateList.add(resultList.get(i).TransactionDate);
            }
        }

        for (int i = 0; i < dateList.size(); i++) {
            TransactionObject header = new TransactionObject();
            header.TransactionDate = dateList.get(i);
            header.TransactionType = TransactionObject.TYPE.HEADER;
            transactionList.add(header);
            for (int j = 0; j < resultList.size(); j++) {
                TransactionObject itemObject = resultList.get(j);
                itemObject.TransactionType = TransactionObject.TYPE.ITEM;
                if (header.TransactionDate.equalsIgnoreCase(itemObject.TransactionDate)) {
                    transactionList.add(itemObject);
                }
            }
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listviewAdapter.addAllItem(transactionList);
                listviewAdapter.notifyDataSetChanged();
            }
        });
    }

    public class CustomListviewTooltipAdapter extends ArrayAdapter<CardObject> {
        @NonNull
        Context context;
        @LayoutRes
        int resource;
        @NonNull
        List<CardObject> objects;

        public CustomListviewTooltipAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CardObject> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.objects = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.item_tooltip, null);
            TextView tooltip_item_title = (TextView) view.findViewById(R.id.tooltip_item_title);
            ImageView tooltip_item_img = (ImageView) view.findViewById(R.id.tooltip_item_img);
            tooltip_item_title.setText(objects.get(position).CardNumber);
            if (objects.get(position).IsChecked) {
                tooltip_item_img.setBackgroundResource(R.drawable.check);
            } else {
                tooltip_item_img.clearColorFilter();
            }
            return view;
        }
    }

    public class CustomListviewAdapter extends BaseAdapter {

        private List<TransactionObject> mData = new ArrayList<>();

        private LayoutInflater mInflater;

        public CustomListviewAdapter(Context context) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addAllItem(List<TransactionObject> itemList) {
            mData.clear();
            mData.addAll(itemList);
        }


        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public TransactionObject getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                switch (mData.get(position).TransactionType) {
                    case ITEM:
                        convertView = mInflater.inflate(R.layout.item_lv_history, null);
                        holder.txtHistory_Title = (TextView) convertView.findViewById(R.id.txtHistory_Title);
                        holder.txtHistory_Cost = (TextView) convertView.findViewById(R.id.txtHistory_Cost);
                        holder.txtHistory_Code = (TextView) convertView.findViewById(R.id.txtHistory_Code);
                        holder.txtHistory_Title.setText(mData.get(position).Description);
                        holder.txtHistory_Cost.setText(String.valueOf(mData.get(position).TransactionAmount));
                        holder.txtHistory_Code.setText(mData.get(position).TransactionCurrencyCode);
                        break;
                    case HEADER:
                        convertView = mInflater.inflate(R.layout.item_lv_header_history, null);
                        holder.txtHistory_Title = (TextView) convertView.findViewById(R.id.txtDateHeaderHistory);
                        holder.txtHistory_Title.setText(DateUtil.getDateHistory(mData.get(position).TransactionDate));
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        public class ViewHolder {
            public TextView txtHistory_Title;
            public TextView txtHistory_Cost;
            public TextView txtHistory_Code;
        }

    }

    public class ItemsHistory {
        private String title;
        private String cost;
        private String code;
        private Date date;

        public ItemsHistory(String title, String cost, String code, Date date) {
            this.title = title;
            this.cost = cost;
            this.code = code;
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }


}
