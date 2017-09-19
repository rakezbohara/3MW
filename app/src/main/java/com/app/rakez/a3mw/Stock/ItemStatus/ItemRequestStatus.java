package com.app.rakez.a3mw.Stock.ItemStatus;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.Item;
import com.app.rakez.a3mw.datastore.NewStockRequest;
import com.app.rakez.a3mw.datastore.StockItemStatusReq;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemRequestStatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemRequestStatus extends Fragment{

    RecyclerView requestItemRV;
    ItemRequestStatusAdapter itemRequestStatusAdapter;
    FloatingActionButton fab;
    List<ItemRequestStatusItem> itemRequestStatusItemList = new ArrayList<>();

    Dialog dialog;
    EditText itemName_et,qty,reqNote;
    Spinner itemName_sp,priority;
    List<String> itemNameData = new ArrayList<String>();
    List<String> priorityData = new ArrayList<String>();
    ArrayAdapter<String> itemNameAdapter;
    ArrayAdapter<String> priorityAdapter;
    Button request;
    String userId;
    String token;




    private static final String ARG_PARAM1 = "param1";
    private String pId;


    public ItemRequestStatus() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ItemRequestStatus.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemRequestStatus newInstance(String param1) {
        ItemRequestStatus fragment = new ItemRequestStatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_request_status, container, false);
        initialize(view);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        requestItemRV.setLayoutManager(rLayoutManager);
        requestItemRV.setItemAnimator(new DefaultItemAnimator());
        requestItemRV.setAdapter(itemRequestStatusAdapter);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("userinfo", 0);
        userId = pref.getString("id", "");
        token = pref.getString("token", "");
        List<StockItemStatusReq> loadTask = StockItemStatusReq.find(StockItemStatusReq.class,"p_id=?",pId);
        for(int i =0 ; i< loadTask.size();i++){
            StockItemStatusReq p = loadTask.get(i);
            itemRequestStatusItemList.add(new ItemRequestStatusItem(p.getItemId(),p.getItemName(),p.getReq_date(),p.getChallaniNo(),p.getQuantity(),p.getQty_sent(),p.getQty_received(),p.getItemStatus()));
        }
        itemRequestStatusAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_request_item);
                dialog.setTitle("Request");
                itemNameAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,itemNameData);
                priorityAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,priorityData);
                itemName_et = (EditText) dialog.findViewById(R.id.dialog_request_item_itemname_et);
                qty = (EditText) dialog.findViewById(R.id.dialog_request_item_qty);
                reqNote = (EditText) dialog.findViewById(R.id.dialog_request_item_req_note);
                itemName_sp = (Spinner) dialog.findViewById(R.id.dialog_request_item_itemname_sp);
                priority = (Spinner) dialog.findViewById(R.id.dialog_request_item_priority);
                request = (Button) dialog.findViewById(R.id.dialog_request_item_request);
                itemName_et.setFocusableInTouchMode(false);
                itemName_et.setFocusable(false);
                itemName_sp.setAdapter(itemNameAdapter);
                priority.setAdapter(priorityAdapter);
                final List<Item> items = Item.find(Item.class,"p_id=?",pId);
                itemNameData.clear();
                for(int i =0 ; i< items.size();i++){
                    Item p = items.get(i);
                    itemNameData.add(p.getName());
                }
                itemNameAdapter.notifyDataSetChanged();
                itemNameData.add("Other");
                priorityData.add("High");
                priorityData.add("Low");
                itemNameAdapter.notifyDataSetChanged();
                priorityAdapter.notifyDataSetChanged();
                request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String newItem = itemName_et.getText().toString();
                        String quantity = qty.getText().toString();
                        int priorPos  = priority.getSelectedItemPosition();
                        String prior;
                        if(priorPos==0){
                            prior = "high";
                        }else{
                            prior = "low";
                        }
                        String note = reqNote.getText().toString();
                        String item_id;
                        int itemNamepos = itemName_sp.getSelectedItemPosition();
                        if(itemNameAdapter.getItem(itemNamepos).equals("Other")){
                            item_id = null;
                        }else{
                            Item selItem = items.get(itemNamepos);
                            item_id = selItem.getItemId();
                        }
                        Log.d("Data",newItem+";"+quantity+";"+prior+";"+note+";"+item_id);
                        if(quantity.equals("") || note.equals("")){
                            qty.setError("Required");
                            reqNote.setError("Required");
                        }else{
                            NewStockRequest newStockRequest = new NewStockRequest(userId,token,item_id,newItem,pId,quantity,prior,note);
                            newStockRequest.save();
                            long a = newStockRequest.save();
                            if(a!=-1){
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success!")
                                        .setContentText("Data is saved. You can now manually sync!")
                                        .show();
                            }else{
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong! Please Try Again")
                                        .show();
                            }
                        }

                        dialog.dismiss();
                        itemNameData.clear();
                        priorityData.clear();

                    }
                });
                itemName_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(adapterView.getSelectedItem().toString().equals("Other")){
                            Log.d("Event","Action performend Item selected"+adapterView.getSelectedItem().toString());
                            itemName_et.setFocusableInTouchMode(true);
                            itemName_et.setFocusable(true);
                        }else{
                            itemName_et.setFocusableInTouchMode(false);
                            itemName_et.setFocusable(false);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                dialog.show();
            }
        });
        requestItemRV.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
        return view;
    }

    private void initialize(View view) {

        requestItemRV = (RecyclerView) view.findViewById(R.id.fragment_item_request_status_itemRV);
        itemRequestStatusAdapter = new ItemRequestStatusAdapter(getActivity(),itemRequestStatusItemList,pId);
        fab = (FloatingActionButton) view.findViewById(R.id.fragment_item_request_status_fab);
    }



}
