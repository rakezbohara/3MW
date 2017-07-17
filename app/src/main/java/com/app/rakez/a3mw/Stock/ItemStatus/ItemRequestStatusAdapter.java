package com.app.rakez.a3mw.Stock.ItemStatus;

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
import android.widget.Toast;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.ItemReceivedReq;

import java.util.List;

/**
 * Created by RAKEZ on 7/3/2017.
 */

public class ItemRequestStatusAdapter extends RecyclerView.Adapter<ItemRequestStatusAdapter.MyViewHolder>  {

    private Context mContext;
    private List<ItemRequestStatusItem> itemRequestStatusItemList;
    private String pId;


    public ItemRequestStatusAdapter(Context mContext, List<ItemRequestStatusItem> itemRequestStatusItemList, String pId) {
        this.mContext = mContext;
        this.itemRequestStatusItemList = itemRequestStatusItemList;
        this.pId = pId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemrequestitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ItemRequestStatusItem itemRequestStatusItem = itemRequestStatusItemList.get(position);
        holder.itemName.setText(itemRequestStatusItem.getItemName());
        holder.date.setText(itemRequestStatusItem.getDate());
        holder.totalQty.setText(itemRequestStatusItem.getTotalQty());
        holder.qtyReceived.setText(itemRequestStatusItem.getQtyReceived());
        holder.qtySent.setText(itemRequestStatusItem.getQtySent());
        holder.status.setText(itemRequestStatusItem.getStatus());
        if(itemRequestStatusItem.getStatus().equals("Request Pending")){
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.requestPending));
        } else if(itemRequestStatusItem.getStatus().equals("Request Approved")){
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.requestApproved));
        } else if(itemRequestStatusItem.getStatus().equals("Item Received")){
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.itemReceived));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.status.getText().toString().equals("Request Approved")){
                    TextView qtyRequested, qtySent, challaniNo;
                    final EditText qtyReceived;
                    Button save;
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setTitle("Approve Request");
                    dialog.setContentView(R.layout.dialog_request_approve);
                    SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("userinfo", 0);
                    final String userId = pref.getString("id", "");
                    final String token = pref.getString("token", "");
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    qtyRequested = (TextView) dialog.findViewById(R.id.dialog_request_approve_qtyRequested);
                    qtySent = (TextView) dialog.findViewById(R.id.dialog_request_approve_qtySent);
                    challaniNo = (TextView) dialog.findViewById(R.id.dialog_request_approve_challaniNo);
                    qtyReceived  = (EditText) dialog.findViewById(R.id.dialog_request_approve_qtyReceived);
                    qtyRequested.setText(itemRequestStatusItem.getTotalQty());
                    qtySent.setText(itemRequestStatusItem.getQtySent());
                    challaniNo.setText(itemRequestStatusItem.getChallaniNo());
                    save = (Button) dialog.findViewById(R.id.dialog_request_approve_enter);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String quantity = qtyReceived.getText().toString();
                            if(quantity.equals("")){
                                qtyReceived.setError("Required");
                            }else{
                                ItemReceivedReq itemReceivedReq = new ItemReceivedReq(userId, token, itemRequestStatusItem.getItemId(),pId,quantity,itemRequestStatusItem.getChallaniNo());
                                itemReceivedReq.save();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemRequestStatusItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView itemName;
        public TextView date;
        public TextView totalQty;
        public TextView qtySent;
        public TextView qtyReceived;
        public TextView status;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemName = (TextView) itemView.findViewById(R.id.itemrequestitem_itemname);
            date = (TextView) itemView.findViewById(R.id.itemrequestitem_date);
            totalQty = (TextView) itemView.findViewById(R.id.itemrequestitem_qtytotal);
            qtySent = (TextView) itemView.findViewById(R.id.itemrequestitem_qtysent);
            qtyReceived = (TextView) itemView.findViewById(R.id.itemrequestitem_qtyreceived);
            status = (TextView) itemView.findViewById(R.id.itemrequestitem_status);
        }
    }

}
