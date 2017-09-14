package com.app.rakez.a3mw.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.SubTask;
import com.app.rakez.a3mw.datastore.TaskRecord;

import java.util.ArrayList;
import java.util.List;

public class Tasks extends AppCompatActivity {

    RecyclerView taskRV;
    TaskAdapter tAdapter;
    List<TaskItem> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        String pId = getIntent().getStringExtra("P_ID");
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_tasks_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialize();
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(rLayoutManager);
        taskRV.setItemAnimator(new DefaultItemAnimator());
        taskRV.setAdapter(tAdapter);
        List<SubTask> loadTask = SubTask.find(SubTask.class,"p_id=?",pId);
        for(int i =0 ; i< loadTask.size();i++){
            SubTask p = loadTask.get(i);
            taskList.add(new TaskItem(p.gettId(),p.getJob()+"("+p.getDiameter()+" X "+p.getThickness() +")",p.getTotal_requirement(),p.getC_fabrication(),p.getC_erection()));
        }
        tAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        taskRV = (RecyclerView) findViewById(R.id.taskRV);
        tAdapter = new TaskAdapter(this,taskList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
