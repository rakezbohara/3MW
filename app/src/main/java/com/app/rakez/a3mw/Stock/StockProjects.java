package com.app.rakez.a3mw.Stock;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.rakez.a3mw.MainActivity;
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
import com.app.rakez.a3mw.projects.Projects;
import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.services.DataExchange;

import java.util.ArrayList;
import java.util.List;

public class StockProjects extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView stockProjectRV;
    StockProjectAdapter sProjectAdapter;
    List<StockProjectItem> sProjectList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_project_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_stock_projects_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_stock_projects_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navName = (TextView) headerView.findViewById(R.id.nav_name);
        TextView navPosition = (TextView) headerView.findViewById(R.id.nav_position);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        String name = pref.getString("name", "");
        String position = pref.getString("role", "");
        navName.setText(name);
        navPosition.setText(position);
        initialize();
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        stockProjectRV.setLayoutManager(rLayoutManager);
        stockProjectRV.setItemAnimator(new DefaultItemAnimator());
        stockProjectRV.setAdapter(sProjectAdapter);
        loadDataToAdapter();
    }
    public void initialize(){
        stockProjectRV = (RecyclerView) findViewById(R.id.stockprojectRV);
        sProjectAdapter = new StockProjectAdapter(this,sProjectList);
    }
    public void loadDataToAdapter(){
        List<StockProject> loadProject = StockProject.listAll(StockProject.class);
        sProjectList.clear();
        for(int i =0 ; i< loadProject.size();i++){
            StockProject p = loadProject.get(i);
            sProjectList.add(new StockProjectItem(p.getpId(),p.getProjectName(),p.getClientName()));
        }
        sProjectAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_projects) {
            Intent in = new Intent(StockProjects.this, Projects.class);
            startActivity(in);
            finish();
        } else if (id == R.id.nav_stock) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_stock_projects_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_stock_projects_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
            progressDialog.dismiss();
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
}
