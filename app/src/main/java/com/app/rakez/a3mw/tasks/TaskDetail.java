package com.app.rakez.a3mw.tasks;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.AddTaskRecord;
import com.app.rakez.a3mw.datastore.TaskRecord;

import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity {

    RecyclerView taskdetailRV;
    TaskDetailAdapter tDetailAdapter;
    FloatingActionButton fab;
    String tId;
    String userId;
    String token;
    List<TaskDetailItem> taskDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        tId = getIntent().getStringExtra("T_ID");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        userId = pref.getString("id", "");
        token = pref.getString("token", "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_task_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialize();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(TaskDetail.this);
                dialog.setContentView(R.layout.dialog_newfaberc);
                final EditText nfab = (EditText) dialog.findViewById(R.id.newfaberc_dialog_fab);
                final EditText nerc = (EditText) dialog.findViewById(R.id.newfaberc_dialog_erc);
                Button nsave = (Button) dialog.findViewById(R.id.newfaberc_dialog_add);
                dialog.setTitle("New Data");
                dialog.show();
                nsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fab = nfab.getText().toString();
                        String erc = nerc.getText().toString();
                        if(fab.equals("") || erc.equals("")){
                            nfab.setError("Required");
                            nerc.setError("Required");
                        }else{
                            AddTaskRecord addTaskRecord = new AddTaskRecord(userId,token,tId,fab,erc);
                            addTaskRecord.save();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        taskdetailRV.setLayoutManager(rLayoutManager);
        taskdetailRV.setItemAnimator(new DefaultItemAnimator());
        taskdetailRV.setAdapter(tDetailAdapter);
        taskdetailRV.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
        List<TaskRecord> loadTask = TaskRecord.find(TaskRecord.class,"subtasksid=?",tId);
        for(int i =0 ; i< loadTask.size();i++){
            TaskRecord p = loadTask.get(i);
            taskDetailList.add(new TaskDetailItem(p.getTrId(),p.getDate(),p.getFabrication(),p.getC_fabrication(),p.getErection(),p.getC_erection()));
        }
        tDetailAdapter.notifyDataSetChanged();

    }

    private void initialize() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        taskdetailRV = (RecyclerView) findViewById(R.id.taskdetailRV);
        tDetailAdapter = new TaskDetailAdapter(this,taskDetailList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
