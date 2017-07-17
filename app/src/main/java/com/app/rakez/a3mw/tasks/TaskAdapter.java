package com.app.rakez.a3mw.tasks;

import android.content.Context;
import android.content.Intent;
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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{

    private Context mContext;
    private List<TaskItem> taskList;

    public TaskAdapter(Context mContext, List<TaskItem> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TaskItem projectItem = taskList.get(position);
        holder.taskName.setText(projectItem.getTaskName());
        holder.totalRequirement.setText(projectItem.getTotalRequirement());
        holder.fabrication.setText(projectItem.getFabrication());
        holder.erection.setText(projectItem.getErection());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, TaskDetail.class);
                in.putExtra("T_ID", projectItem.gettId());
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView taskName;
        public TextView totalRequirement;
        public TextView fabrication;
        public TextView erection;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            taskName = (TextView) itemView.findViewById(R.id.taskname);
            totalRequirement = (TextView) itemView.findViewById(R.id.totalRequirement);
            fabrication = (TextView) itemView.findViewById(R.id.fabrication);
            erection = (TextView) itemView.findViewById(R.id.erection);

        }
    }
}
