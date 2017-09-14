package com.app.rakez.a3mw.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.tasks.Tasks;

import java.util.List;

/**
 * Created by RAKEZ on 6/30/2017.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProjectItem> projectList;

    public ProjectAdapter(Context mContext, List<ProjectItem> projectList) {
        this.mContext = mContext;
        this.projectList = projectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projectitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ProjectItem projectItem = projectList.get(position);
        holder.projectName.setText(projectItem.getProjectName());
        holder.designer.setText(projectItem.getClientName());
        holder.progressPercentage.setText(projectItem.getProgressPercentage()+"%");
        holder.progressBar.setProgress((int)Float.parseFloat(projectItem.getProgressPercentage()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent in = new Intent(mContext, Tasks.class);
                    in.putExtra("P_ID", projectItem.getpId());
                    mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView projectName;
        public TextView status;
        public TextView designer;
        public TextView progressPercentage;
        public ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            projectName = (TextView) itemView.findViewById(R.id.projectname);
            status = (TextView) itemView.findViewById(R.id.status);
            designer = (TextView) itemView.findViewById(R.id.designername);
            progressPercentage = (TextView) itemView.findViewById(R.id.progressPercentage);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
