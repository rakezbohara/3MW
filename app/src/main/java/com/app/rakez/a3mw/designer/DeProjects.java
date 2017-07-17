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
import com.app.rakez.a3mw.datastore.Project;
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
            projectList.add(new ProjectItem(p.getProjectName(),p.getpId(),p.getStatus(),p.getDesigner(),p.getStartDate(),p.getEndDate()));
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
            //refreshDB();
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
}
