package com.app.rakez.a3mw.designer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.DeSubTask;

import java.util.ArrayList;
import java.util.List;

public class DeTask extends AppCompatActivity {
    RecyclerView taskRV;
    DeTaskAdapter tAdapter;
    List<DeTaskItem> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_task);
        String pId = getIntent().getStringExtra("P_ID");
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_de_tasks_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialize();
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(rLayoutManager);
        taskRV.setItemAnimator(new DefaultItemAnimator());
        taskRV.setAdapter(tAdapter);
        List<DeSubTask> loadTask = DeSubTask.find(DeSubTask.class,"p_id=?",pId);
        taskList.clear();
        for(int i =0 ; i< loadTask.size();i++){
            DeSubTask p = loadTask.get(i);
            taskList.add(new DeTaskItem(p.gettId(),p.getJob()+"("+p.getDiameter()+" X "+p.getThickness() +")",p.getTotal_requirement(),p.getUnit_weight(),p.getUnit()));
        }
        tAdapter.notifyDataSetChanged();

    }
    private void initialize() {
        taskRV = (RecyclerView) findViewById(R.id.activity_de_tasks_taskRV);
        tAdapter = new DeTaskAdapter(this,taskList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
