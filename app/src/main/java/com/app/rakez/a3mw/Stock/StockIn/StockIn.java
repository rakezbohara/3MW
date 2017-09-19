package com.app.rakez.a3mw.Stock.StockIn;

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

public class StockIn extends AppCompatActivity {

    RecyclerView itemRV;
    StockInAdapter sInAdapter;
    List<StockInItem> sInItem = new ArrayList<>();
    String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);
        pId = getIntent().getStringExtra("P_ID");
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_stock_in_toolbar);
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
        itemRV.setAdapter(sInAdapter);
        List<com.app.rakez.a3mw.datastore.StockIn> loadProject = com.app.rakez.a3mw.datastore.StockIn.find(com.app.rakez.a3mw.datastore.StockIn.class,"p_id=?",pId);
        for(int i =0 ; i< loadProject.size();i++){
            com.app.rakez.a3mw.datastore.StockIn p = loadProject.get(i);
            sInItem.add(new StockInItem(p.getItemId(),p.getItemName(),p.getSentDate(),p.getQtyReceived(),p.getChallaniNo()));
        }
        sInAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        itemRV = (RecyclerView) findViewById(R.id.activity_stock_in_itemRV);
        sInAdapter = new StockInAdapter(this,sInItem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
