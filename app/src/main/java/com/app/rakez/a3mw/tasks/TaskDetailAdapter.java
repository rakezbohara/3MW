package com.app.rakez.a3mw.tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.rakez.a3mw.R;

import java.util.List;

/**
 * Created by RAKEZ on 7/2/2017.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<TaskDetailItem> taskDetailList;

    public TaskDetailAdapter(Context mContext, List<TaskDetailItem> taskDetailList) {
        this.mContext = mContext;
        this.taskDetailList = taskDetailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskdetailitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskDetailItem taskDetailItem = taskDetailList.get(position);
        holder.taskdetailitem_date.setText(taskDetailItem.getDate());
        holder.taskdetailitem_fabrication.setText(taskDetailItem.getFabrication());
        holder.taskdetailitem_cfabrication.setText(taskDetailItem.getcFabrication());
        holder.taskdetailitem_erection.setText(taskDetailItem.getErection());
        holder.taskdetailitem_cerection.setText(taskDetailItem.getcErection());
    }

    @Override
    public int getItemCount() {
        return taskDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView taskdetailitem_date;
        public TextView taskdetailitem_fabrication;
        public TextView taskdetailitem_cfabrication;
        public TextView taskdetailitem_erection;
        public TextView taskdetailitem_cerection;
        public MyViewHolder(View itemView) {
            super(itemView);
            taskdetailitem_date = (TextView) itemView.findViewById(R.id.taskdetailitem_date);
            taskdetailitem_fabrication = (TextView) itemView.findViewById(R.id.taskdetailitem_fabrication);
            taskdetailitem_cfabrication = (TextView) itemView.findViewById(R.id.taskdetailitem_cfabrication);
            taskdetailitem_erection = (TextView) itemView.findViewById(R.id.taskdetailitem_erection);
            taskdetailitem_cerection = (TextView) itemView.findViewById(R.id.taskdetailitem_cerection);
        }
    }
}
