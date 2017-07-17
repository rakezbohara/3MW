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
import com.app.rakez.a3mw.datastore.ItemReceivedSent;

import java.util.List;

/**
 * Created by RAKEZ on 7/4/2017.
 */

public class ItemSentAdminAdapter extends RecyclerView.Adapter<ItemSentAdminAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemSentAdminItem> itemSentAdminItemList;
    private String pId;

    public ItemSentAdminAdapter(Context mContext, List<ItemSentAdminItem> itemSentAdminItemList, String pId) {
        this.mContext = mContext;
        this.itemSentAdminItemList = itemSentAdminItemList;
        this.pId = pId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemsentitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ItemSentAdminItem itemSentAdminItem = itemSentAdminItemList.get(position);
        holder.itemName.setText(itemSentAdminItem.getItemName());
        holder.date.setText(itemSentAdminItem.getDate());
        holder.challaniNo.setText(itemSentAdminItem.getChallaniNo());
        holder.qtyReceived.setText(itemSentAdminItem.getQtyReceived());
        holder.qtySent.setText(itemSentAdminItem.getQtySent());
        holder.status.setText(itemSentAdminItem.getStatus());
        if(itemSentAdminItem.getStatus().equals("Item Sent")){
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.requestApproved));
        } else if(itemSentAdminItem.getStatus().equals("Item Received")){
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.itemReceived));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.status.getText().toString().equals("Item Sent")){
                    TextView qtySent, challaniNo;
                    final EditText qtyReceived;
                    Button save;
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setTitle("Approve Request");
                    dialog.setContentView(R.layout.dialog_admin_request_approve);
                    SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("userinfo", 0);
                    final String userId = pref.getString("id", "");
                    final String token = pref.getString("token", "");
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    qtySent = (TextView) dialog.findViewById(R.id.dialog_admin_request_approve_qtySent);
                    challaniNo = (TextView) dialog.findViewById(R.id.dialog_admin_request_approve_challaniNo);
                    qtyReceived  = (EditText) dialog.findViewById(R.id.dialog_admin_request_approve_qtyReceived);
                    qtySent.setText(itemSentAdminItem.getQtySent());
                    challaniNo.setText(itemSentAdminItem.getChallaniNo());
                    save = (Button) dialog.findViewById(R.id.dialog_admin_request_approve_enter);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String quantity = qtyReceived.getText().toString();
                            if(quantity.equals("")){
                                qtyReceived.setError("Required");
                            }else{
                                ItemReceivedSent itemReceivedSent = new ItemReceivedSent(userId, token, itemSentAdminItem.getItemId(),pId,quantity,itemSentAdminItem.getChallaniNo());
                                itemReceivedSent.save();
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
        return itemSentAdminItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView itemName;
        public TextView date;
        public TextView challaniNo;
        public TextView qtySent;
        public TextView qtyReceived;
        public TextView status;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemName = (TextView) itemView.findViewById(R.id.itemsentitem_itemname);
            date = (TextView) itemView.findViewById(R.id.itemsentitem_date);
            challaniNo = (TextView) itemView.findViewById(R.id.itemsentitem_challani);
            qtySent = (TextView) itemView.findViewById(R.id.itemsentitem_qtysent);
            qtyReceived = (TextView) itemView.findViewById(R.id.itemsentitem_qtyreceived);
            status = (TextView) itemView.findViewById(R.id.itemsentitem_status);
        }
    }
}
