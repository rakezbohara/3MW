package com.app.rakez.a3mw.Stock.StockIn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.rakez.a3mw.R;

import java.util.List;

/**
 * Created by RAKEZ on 7/4/2017.
 */

public class StockInAdapter extends RecyclerView.Adapter<StockInAdapter.MyViewHolder>  {
    private Context mContext;
    private List<StockInItem> stockInItemList;

    public StockInAdapter(Context mContext, List<StockInItem> stockInItemList) {
        this.mContext = mContext;
        this.stockInItemList = stockInItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_in_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StockInItem itemSentAdminItem = stockInItemList.get(position);
        holder.itemName.setText(itemSentAdminItem.getItemName());
        holder.date.setText(itemSentAdminItem.getDate());
        holder.challaniNo.setText(itemSentAdminItem.getChallaniNo());
        holder.qtyReceived.setText(itemSentAdminItem.getQtyReceived());
    }

    @Override
    public int getItemCount() {
        return stockInItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView date;
        public TextView challaniNo;
        public TextView qtyReceived;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.stock_in_item_itemname);
            date = (TextView) itemView.findViewById(R.id.stock_in_item_date);
            challaniNo = (TextView) itemView.findViewById(R.id.stock_in_item_challani);
            qtyReceived = (TextView) itemView.findViewById(R.id.stock_in_item_qtyreceived);
        }
    }
}
