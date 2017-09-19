package com.app.rakez.a3mw.Stock.StockOut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.Project;

import java.util.ArrayList;
import java.util.List;

public class StockOut extends AppCompatActivity {

    RecyclerView itemRV;
    StockOutAdapter sOutAdapter;
    List<StockOutItem> sOutItem = new ArrayList<>();
    String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        pId = getIntent().getStringExtra("P_ID");
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_stock_out_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        List<Project> projects = Project.find(Project.class,"p_id=?",pId);
        Project project = projects.get(0);
        getSupportActionBar().setTitle(project.getProjectName());
        initialize();
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        itemRV.setLayoutManager(rLayoutManager);
        itemRV.setItemAnimator(new DefaultItemAnimator());
        itemRV.setAdapter(sOutAdapter);
        List<com.app.rakez.a3mw.datastore.StockOut> loadProject = com.app.rakez.a3mw.datastore.StockOut.find(com.app.rakez.a3mw.datastore.StockOut.class,"p_id=?",pId);
        for(int i =0 ; i< loadProject.size();i++){
            com.app.rakez.a3mw.datastore.StockOut p = loadProject.get(i);
            sOutItem.add(new StockOutItem(p.getItemId(),p.getItemName(),p.getCurrent_stock()));
        }
        sOutAdapter.notifyDataSetChanged();
    }
    private void initialize() {
        itemRV = (RecyclerView) findViewById(R.id.activity_stock_out_itemRV);
        sOutAdapter = new StockOutAdapter(this,sOutItem,pId);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
