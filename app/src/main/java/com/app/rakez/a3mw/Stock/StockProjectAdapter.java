package com.app.rakez.a3mw.Stock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.Stock.ItemStatus.ItemStatus;
import com.app.rakez.a3mw.Stock.StockIn.StockIn;
import com.app.rakez.a3mw.Stock.StockOut.StockOut;

import java.util.List;

/**
 * Created by RAKEZ on 7/3/2017.
 */

public class StockProjectAdapter extends RecyclerView.Adapter<StockProjectAdapter.MyViewHolder> {

    private Context mContext;
    private List<StockProjectItem> stockProjectList;

    public StockProjectAdapter(Context mContext, List<StockProjectItem> stockProjectList) {
        this.mContext = mContext;
        this.stockProjectList = stockProjectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stockprojectitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final StockProjectItem stockProjectItem = stockProjectList.get(position);
        holder.projectName.setText(stockProjectItem.getProjectName());
        holder.designer.setText(stockProjectItem.getClientName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, ItemStatus.class);
                in.putExtra("P_ID", stockProjectItem.getpId());
                mContext.startActivity(in);
            }
        });
        holder.stockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, StockIn.class);
                in.putExtra("P_ID", stockProjectItem.getpId());
                mContext.startActivity(in);
            }
        });
        holder.stockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, StockOut.class);
                in.putExtra("P_ID", stockProjectItem.getpId());
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockProjectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView projectName;
        public TextView status;
        public TextView designer;
        public Button stockIn;
        public Button stockOut;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            projectName = (TextView) itemView.findViewById(R.id.stockprojectitem_projectname);
            status = (TextView) itemView.findViewById(R.id.stockprojectitem_status);
            designer = (TextView) itemView.findViewById(R.id.stockprojectitem_designername);
            stockIn = (Button) itemView.findViewById(R.id.stockprojectitem_stockin);
            stockOut = (Button) itemView.findViewById(R.id.stockprojectitem_stockout);
        }
    }
}
