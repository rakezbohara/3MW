package com.app.rakez.a3mw.Stock.ItemStatus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.StockItemStatusSent;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemSentAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemSentAdmin extends Fragment {

    RecyclerView sentItemRV;
    ItemSentAdminAdapter itemSentAdminAdapter;
    List<ItemSentAdminItem> itemSentAdminItemList = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";

    private String pId;


    public ItemSentAdmin() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemSentAdmin newInstance(String param1) {
        ItemSentAdmin fragment = new ItemSentAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_sent_admin, container, false);
        initialize(view);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        sentItemRV.setLayoutManager(rLayoutManager);
        sentItemRV.setItemAnimator(new DefaultItemAnimator());
        sentItemRV.setAdapter(itemSentAdminAdapter);
        List<StockItemStatusSent> loadTask = StockItemStatusSent.find(StockItemStatusSent.class,"p_id=?",pId);
        for(int i =0 ; i< loadTask.size();i++){
            StockItemStatusSent p = loadTask.get(i);
            itemSentAdminItemList.add(new ItemSentAdminItem(p.getItemId(),p.getItemName(),p.getSent_date(),p.getChallaniNo(),p.getQty_sent(),p.getQty_received(),p.getItemStatus()));
        }
        itemSentAdminAdapter.notifyDataSetChanged();
        return view;
    }

    private void initialize(View view) {
        sentItemRV = (RecyclerView) view.findViewById(R.id.fragment_item_sent_admin_itemRV);
        itemSentAdminAdapter = new ItemSentAdminAdapter(getActivity(),itemSentAdminItemList, pId);
    }

}
