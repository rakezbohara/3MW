package com.app.rakez.a3mw.Stock.StockOut;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.AddStockOut;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RAKEZ on 7/6/2017.
 */

public class StockOutAdapter extends RecyclerView.Adapter<StockOutAdapter.MyViewHolder> {
    private Context mContext;
    private List<StockOutItem> stockOutItemList;
    private String pId;

    public StockOutAdapter(Context mContext, List<StockOutItem> stockOutItemList, String pId) {
        this.mContext = mContext;
        this.stockOutItemList = stockOutItemList;
        this.pId = pId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_out_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final StockOutItem stockOutItem = stockOutItemList.get(position);
        holder.itemName.setText(stockOutItem.getItemName());
        holder.qty.setText(stockOutItem.getQty());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText stockOutQty;
                Button stockOutEnter;
                final Dialog dialog = new Dialog(mContext);
                dialog.setTitle("Stock Out");
                dialog.setContentView(R.layout.dialog_stock_out);
                stockOutQty = (EditText) dialog.findViewById(R.id.dialog_stock_out_qty);
                stockOutEnter = (Button) dialog.findViewById(R.id.dialog_stock_out_enter);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                stockOutEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("userinfo", 0);
                        String userId = pref.getString("id", "");
                        String token = pref.getString("token", "");
                        String qty_used = stockOutQty.getText().toString();
                        if(qty_used.equals("")){
                            stockOutQty.setError("Required");
                        }else{
                            int totalStock = Integer.parseInt(stockOutItem.getQty());
                            int req_stock = Integer.parseInt(qty_used);
                            if(req_stock>totalStock){
                                stockOutQty.setError("Quantity Exceeds");
                            }else{
                                AddStockOut addStockOut = new AddStockOut(userId,token,stockOutItem.getItemId(),pId,qty_used);
                                long a = addStockOut.save();
                                if(a!=-1){
                                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success!")
                                            .setContentText("Data is saved. You can now manually sync!")
                                            .show();
                                }else{
                                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Something went wrong! Please Try Again")
                                            .show();
                                }
                                dialog.dismiss();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockOutItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        public TextView itemName;
        public TextView qty;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemName = (TextView) itemView.findViewById(R.id.stock_out_item_itemname);
            qty = (TextView) itemView.findViewById(R.id.stock_out_item_qty);
        }
    }
}
