package com.app.rakez.a3mw.designer;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.rakez.a3mw.MainActivity;
import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.AddStockOut;
import com.app.rakez.a3mw.datastore.AddTaskRecord;
import com.app.rakez.a3mw.datastore.DeInput;
import com.app.rakez.a3mw.datastore.DeSubTask;
import com.app.rakez.a3mw.datastore.Item;
import com.app.rakez.a3mw.datastore.ItemReceivedReq;
import com.app.rakez.a3mw.datastore.ItemReceivedSent;
import com.app.rakez.a3mw.datastore.NewStockRequest;
import com.app.rakez.a3mw.datastore.Project;
import com.app.rakez.a3mw.datastore.StockIn;
import com.app.rakez.a3mw.datastore.StockItemStatusReq;
import com.app.rakez.a3mw.datastore.StockItemStatusSent;
import com.app.rakez.a3mw.datastore.StockOut;
import com.app.rakez.a3mw.datastore.StockProject;
import com.app.rakez.a3mw.datastore.SubTask;
import com.app.rakez.a3mw.datastore.TaskRecord;
import com.app.rakez.a3mw.projects.ProjectItem;
import com.app.rakez.a3mw.services.DataExchange;

import java.util.ArrayList;
import java.util.List;

public class DeProjects extends AppCompatActivity {

    RecyclerView projectRV;
    DeProjectAdapter pAdapter;
    List<ProjectItem> projectList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_de_projects_toolbar);
        setSupportActionBar(toolbar);
        initialize();
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        projectRV.setLayoutManager(rLayoutManager);
        projectRV.setItemAnimator(new DefaultItemAnimator());
        projectRV.setAdapter(pAdapter);
        loadDataToAdapter();
    }

    private void loadDataToAdapter() {
        List<Project> loadProject = Project.listAll(Project.class);
        projectList.clear();
        for(int i =0 ; i< loadProject.size();i++){
            Project p = loadProject.get(i);
            projectList.add(new ProjectItem(p.getProjectName(),p.getpId(),p.getClientName(),p.getProgressPercentage()));
        }
        pAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        projectRV = (RecyclerView) findViewById(R.id.activity_de_projects_itemRV);
        pAdapter = new DeProjectAdapter(this,projectList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }
        if(id==R.id.sync){
            doRefresh();
        }
        if(id==R.id.logout){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            refreshDB();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshDB() {
        //clear all table
        AddStockOut.deleteAll(AddStockOut.class);
        AddTaskRecord.deleteAll(AddTaskRecord.class);
        DeInput.deleteAll(DeInput.class);
        DeSubTask.deleteAll(DeSubTask.class);
        Item.deleteAll(Item.class);
        ItemReceivedSent.deleteAll(ItemReceivedSent.class);
        ItemReceivedReq.deleteAll(ItemReceivedReq.class);
        NewStockRequest.deleteAll(NewStockRequest.class);
        Project.deleteAll(Project.class);
        StockIn.deleteAll(StockIn.class);
        StockItemStatusReq.deleteAll(StockItemStatusReq.class);
        StockItemStatusSent.deleteAll(StockItemStatusSent.class);
        StockOut.deleteAll(StockOut.class);
        StockProject.deleteAll(StockProject.class);
        SubTask.deleteAll(SubTask.class);
        TaskRecord.deleteAll(TaskRecord.class);
    }

    public void doRefresh(){
        Intent intent = new Intent(this, DataExchange.class);
        Log.d("Service Log","onActivity Called");
        startService(intent);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Under Sync");
        progressDialog.setMessage("Please Wait!!! Data is being Updated");
        progressDialog.show();
        Log.d("Service Log","onActivity2 Called");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadDataToAdapter();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(
                DataExchange.BROADCAST_ACTION));
    }
}
