package com.app.rakez.a3mw.designer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.app.rakez.a3mw.R;

import java.util.List;

/**
 * Created by RAKEZ on 7/2/2017.
 */

public class DeTaskAdapter extends RecyclerView.Adapter<DeTaskAdapter.MyViewHolder>{

    private Context mContext;
    private List<DeTaskItem> taskList;

    public DeTaskAdapter(Context mContext, List<DeTaskItem> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detaskitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DeTaskItem projectItem = taskList.get(position);
        holder.taskName.setText(projectItem.getTaskName());
        holder.totalRequirement.setText(projectItem.getTotalRequirement());
        holder.weight.setText(projectItem.getWeight());
        holder.unit.setText(projectItem.getUnit());
        if(projectItem.getWeight()==null && projectItem.getUnit()==null){
            holder.enter.setText("Enter Weight");
        }else{
            holder.enter.setText("Edit Weight");
        }
        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        public TextView weight;
        public TextView unit;
        public Button enter;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            taskName = (TextView) itemView.findViewById(R.id.de_taskname);
            totalRequirement = (TextView) itemView.findViewById(R.id.de_totalRequirement);
            weight = (TextView) itemView.findViewById(R.id.de_weight);
            unit = (TextView) itemView.findViewById(R.id.de_unit);
            enter = (Button) itemView.findViewById(R.id.de_enter);

        }
    }
}
