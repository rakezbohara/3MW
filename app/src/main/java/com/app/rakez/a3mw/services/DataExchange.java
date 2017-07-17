package com.app.rakez.a3mw.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.rakez.a3mw.AppController;
import com.app.rakez.a3mw.R;
import com.app.rakez.a3mw.datastore.AddStockOut;
import com.app.rakez.a3mw.datastore.AddTaskRecord;
import com.app.rakez.a3mw.datastore.Constants;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RAKEZ on 7/11/2017.
 */

public class DataExchange extends Service {

    Map<String, String> params = new HashMap<String, String>();
    private static final String TAG = "ServiceData";
    Intent intent;
    SharedPreferences pref;
    static public String BROADCAST_ACTION = "com.app.rakez.a3mw.broadcastservice";
    boolean terminate = false;
    int i =0;
    //Projects

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service Log","IBinder Called");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service Log","onCreate Called");
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("Service Log","onStart Called");
        pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        String id = pref.getString("id", "");
        String token = pref.getString("token", "");
        params.put("user_id", id);
        params.put("token", token);
        receiveDate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service Log","onDestroy Called");
    }

    private void receiveDate() {
        String role = pref.getString("role", "");
        if(role.equals("design_section")){
            deFetchData();
        }else if(role.equals("production")){
            postTaskRecord();
        }

    }

    private void deFetchData() {

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.PROJECTS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"DeProjects" +response.toString());
                final int oldPLen = maxPID();
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject projects = (JSONObject) response.get(i);
                        if(i==0){
                            if(projects.getString("status").equals("empty")){

                            }else if (projects.getString("status").equals("token_mismatch")){

                            }else if(projects.getString("status").equals("success")){
                                Project.deleteAll(Project.class);
                            }
                        }
                        String active;
                        if(projects.getString("is_active").equals("1")){
                            active = "Active";
                        }else{
                            active = "Inactive";
                        }
                        Project project = new Project(projects.getString("id"),projects.getString("project_name"),active,projects.getString("manager_name"),projects.getString("start_date"),projects.getString("end_date"));
                        project.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                final int newPLen = maxPID();
                if(newPLen > oldPLen){
                    notifyUser(1);
                }
                deloadsubtasks();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);

    }

    private void deloadsubtasks() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.SUBTASKS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"DeSubtasks" +response.toString());

                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject subTaskitem = (JSONObject) response.get(i);
                        if(i==0){
                            if(subTaskitem.getString("status").equals("empty")){

                            }else if (subTaskitem.getString("status").equals("token_mismatch")){


                            }else if(subTaskitem.getString("status").equals("success")){
                                DeSubTask.deleteAll(DeSubTask.class);
                            }
                        }
                        DeSubTask subTask = new DeSubTask(subTaskitem.getString("id"),subTaskitem.getString("project_id"),subTaskitem.getString("job"),subTaskitem.getString("total_requirement"),subTaskitem.getString("unit_weight"),subTaskitem.getString("unit"));
                        subTask.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sendBroadcast(intent);
                stopSelf();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void postTaskRecord(){
        List<AddTaskRecord> addTaskRecords = AddTaskRecord.listAll(AddTaskRecord.class);
        int len = addTaskRecords.size();
        for(int i = len; i > 0; i--){
            if(i==1){
                terminate = true;
            }
            final AddTaskRecord taskRecord = addTaskRecords.get(i-1);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", taskRecord.getUserId());
                params.put("token", taskRecord.getToken());
                params.put("sId", taskRecord.getsId());
                params.put("fab", taskRecord.getFab());
                params.put("erc", taskRecord.getErc());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL+Constants.ADDTASKRECORD_URL,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"addtaskrecord" +response.toString());
                    try {
                        JSONObject itemsJSON =response;
                        if(itemsJSON.getString("status").equals("empty")){

                        }else if (itemsJSON.getString("status").equals("token_mismatch")){
                            Log.d("TAG","Token Mismatch Matched");
                            clearSession();
                        }else if(itemsJSON.getString("status").equals("success")){
                            taskRecord.delete();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(terminate){
                        terminate = false;
                        postItemRequest();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    stopSelf();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrReq);

        }
        postItemRequest();
    }

    private void postItemRequest(){
        List<NewStockRequest> newStockRequests = NewStockRequest.listAll(NewStockRequest.class);
        int len = newStockRequests.size();
        for(int i = len; i > 0; i--){
            if(i==1){
                terminate = true;
            }
            final NewStockRequest newRequest = newStockRequests.get(i-1);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", newRequest.getUserId());
                params.put("token", newRequest.getToken());
                params.put("item_id", newRequest.getItemId());
                params.put("new_item", newRequest.getNewItem());
                params.put("p_id", newRequest.getpId());
                params.put("quantity", newRequest.getQuantity());
                params.put("priority", newRequest.getPriority());
                params.put("note", newRequest.getNote());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL+Constants.NEWSTOCKREQUEST_URL,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"addtaskrecord" +response.toString());
                    try {
                        JSONObject stockrequestJSON =response;
                        if(stockrequestJSON.getString("status").equals("empty")){

                        }else if (stockrequestJSON.getString("status").equals("token_mismatch")){
                            clearSession();

                        }else if(stockrequestJSON.getString("status").equals("success")){
                            newRequest.delete();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(terminate){
                        terminate = false;
                        postReceivedReq();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    stopSelf();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrReq);

        }
        postReceivedReq();
    }

    private void postReceivedReq(){
        List<ItemReceivedReq> itemReceivedReqs = ItemReceivedReq.listAll(ItemReceivedReq.class);
        final int lastpos = itemReceivedReqs.size();
        int len = itemReceivedReqs.size();
        for(int i = len; i > 0; i--){
            if(i==1){
                terminate = true;
            }
            final ItemReceivedReq itemReceivedReq = itemReceivedReqs.get(i-1);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", itemReceivedReq.getUserId());
                params.put("token", itemReceivedReq.getToken());
                params.put("item_id", itemReceivedReq.getItemId());
                params.put("p_id", itemReceivedReq.getpId());
                params.put("quantity", itemReceivedReq.getQuantity());
                params.put("challani_no", itemReceivedReq.getChallaniNo());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL+Constants.ITEMRECEIVEDREQ_URL,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"itemReceivedReq" +response.toString());
                    try {
                        JSONObject stockrequestJSON =response;
                        if(stockrequestJSON.getString("status").equals("empty")){

                        }else if (stockrequestJSON.getString("status").equals("token_mismatch")){
                            clearSession();

                        }else if(stockrequestJSON.getString("status").equals("success")){
                            itemReceivedReq.delete();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(terminate){
                        terminate = false;
                        postReceivedSent();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    stopSelf();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrReq);

        }
        postReceivedSent();
    }

    private void postReceivedSent(){
        List<ItemReceivedSent> itemReceivedSents = ItemReceivedSent.listAll(ItemReceivedSent.class);
        int len = itemReceivedSents.size();
        for(int i = len; i > 0; i--){
            if(i==1){
                terminate = true;
            }
            final ItemReceivedSent itemReceivedSent = itemReceivedSents.get(i-1);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", itemReceivedSent.getUserId());
                params.put("token", itemReceivedSent.getToken());
                params.put("item_id", itemReceivedSent.getItemId());
                params.put("p_id", itemReceivedSent.getpId());
                params.put("quantity", itemReceivedSent.getQuantity());
                params.put("challani_no", itemReceivedSent.getChallaniNo());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL+Constants.ITEMRECEIVEDSENT_URL,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"itemReceivedSent" +response.toString());
                    try {
                        JSONObject stockrequestJSON =response;
                        if(stockrequestJSON.getString("status").equals("empty")){

                        }else if (stockrequestJSON.getString("status").equals("token_mismatch")){
                            clearSession();

                        }else if(stockrequestJSON.getString("status").equals("success")){
                            itemReceivedSent.delete();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(terminate){
                        terminate = false;
                        postStockOut();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    stopSelf();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrReq);

        }
        postStockOut();

    }

    private void postStockOut(){
        List<AddStockOut> addStockOuts = AddStockOut.listAll(AddStockOut.class);
        int len = addStockOuts.size();
        for(int i = len; i > 0; i--){
            if(i==1){
                terminate = true;
            }
            final AddStockOut addStockOut = addStockOuts.get(i-1);
            JSONObject params = new JSONObject();
            try {
                params.put("user_id", addStockOut.getUserId());
                params.put("token", addStockOut.getToken());
                params.put("item_id", addStockOut.getItemId());
                params.put("p_id", addStockOut.getpId());
                params.put("quantity", addStockOut.getQuantity());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL+Constants.ADDSTOCKOUT_URL,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"postStockout" +response.toString());
                    try {
                        JSONObject stockrequestJSON =response;
                        if(stockrequestJSON.getString("status").equals("empty")){

                        }else if (stockrequestJSON.getString("status").equals("token_mismatch")){
                            //clearSession();

                        }else if(stockrequestJSON.getString("status").equals("success")){
                            addStockOut.delete();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(terminate){
                        terminate = false;
                        loadProjects();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    stopSelf();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrReq);

        }
        loadProjects();

    }

    private void loadProjects() {

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.PROJECTS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"Projects" +response.toString());
                final int oldPLen = maxPID();
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject projects = (JSONObject) response.get(i);
                        if(i==0){
                            if(projects.getString("status").equals("empty")){

                            }else if (projects.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(projects.getString("status").equals("success")){
                                Project.deleteAll(Project.class);
                            }
                        }
                        String active;
                        if(projects.getString("is_active").equals("1")){
                            active = "Active";
                        }else{
                            active = "Inactive";
                        }
                        Project project = new Project(projects.getString("id"),projects.getString("project_name"),active,projects.getString("designer_name"),projects.getString("start_date"),projects.getString("end_date"));
                        project.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                final int newPLen = maxPID();
                if(newPLen > oldPLen){
                    notifyUser(1);
                }
                loadsubtasks();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void loadsubtasks() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.SUBTASKS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"Subtasks" +response.toString());
                final int oldSLen = maxSID();
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject subTaskitem = (JSONObject) response.get(i);
                        if(i==0){
                            if(subTaskitem.getString("status").equals("empty")){

                            }else if (subTaskitem.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(subTaskitem.getString("status").equals("success")){
                                SubTask.deleteAll(SubTask.class);
                            }
                        }
                        SubTask subTask = new SubTask(subTaskitem.getString("id"),subTaskitem.getString("project_id"),subTaskitem.getString("job"),subTaskitem.getString("diameter"),subTaskitem.getString("thickness"),subTaskitem.getString("total_requirement"),subTaskitem.getString("c_fabrication"),subTaskitem.getString("c_erection"));
                        subTask.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                final int newSLen = maxSID();
                if(newSLen > oldSLen){
                    notifyUser(2);
                }
                taskrecords();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void taskrecords() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.TASKRECORDS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"TaskRecords" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject taskRecordItem = (JSONObject) response.get(i);
                        if(i==0){
                            if(taskRecordItem.getString("status").equals("empty")){

                            }else if (taskRecordItem.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(taskRecordItem.getString("status").equals("success")){
                                TaskRecord.deleteAll(TaskRecord.class);
                            }
                        }
                        TaskRecord taskRecord = new TaskRecord(taskRecordItem.getString("id"),taskRecordItem.getString("sub_tasks_id"),taskRecordItem.getString("fabrication"),taskRecordItem.getString("erection"),taskRecordItem.getString("c_fabrication"),taskRecordItem.getString("c_erection"),taskRecordItem.getString("date"));
                        taskRecord.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                stockprojects();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void stockprojects() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.STOCKPROJECTS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"StockProjects" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject stockProjectitem = (JSONObject) response.get(i);
                        if(i==0){
                            if(stockProjectitem.getString("status").equals("empty")){

                            }else if (stockProjectitem.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(stockProjectitem.getString("status").equals("success")){
                                StockProject.deleteAll(StockProject.class);
                            }
                        }
                        String active;
                        if(stockProjectitem.getString("is_active").equals("1")){
                            active = "Active";
                        }else{
                            active = "Inactive";
                        }
                        StockProject stockProject = new StockProject(stockProjectitem.getString("id"),stockProjectitem.getString("project_name"),active,stockProjectitem.getString("designer_name"),stockProjectitem.getString("start_date"),stockProjectitem.getString("end_date"));
                        stockProject.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                stockitemstatusreq();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void stockitemstatusreq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.STOCKITEMSTATUSREQ_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"stockitemstatusreq" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject stockItemStatus = (JSONObject) response.get(i);
                        if(i==0){
                            if(stockItemStatus.getString("status").equals("empty")){

                            }else if (stockItemStatus.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(stockItemStatus.getString("status").equals("success")){
                                StockItemStatusReq.deleteAll(StockItemStatusReq.class);
                            }
                        }
                        StockItemStatusReq stockItem = new StockItemStatusReq(stockItemStatus.getString("id"),stockItemStatus.getString("project_id"),stockItemStatus.getString("item_name"),stockItemStatus.getString("item_status"),stockItemStatus.getString("req_date"),stockItemStatus.getString("quantity"),stockItemStatus.getString("qty_received"),stockItemStatus.getString("qty_sent"),stockItemStatus.getString("challani_no"));
                        stockItem.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                stockitemstatussent();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void stockitemstatussent() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.STOCKITEMSTATUSSENT_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"stockitemstatussent" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject stockItemStatus = (JSONObject) response.get(i);
                        if(i==0){
                            if(stockItemStatus.getString("status").equals("empty")){

                            }else if (stockItemStatus.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(stockItemStatus.getString("status").equals("success")){
                                StockItemStatusSent.deleteAll(StockItemStatusSent.class);
                            }
                        }
                        StockItemStatusSent stockItem = new StockItemStatusSent(stockItemStatus.getString("id"),stockItemStatus.getString("project_id"),stockItemStatus.getString("item_name"),stockItemStatus.getString("item_status"),stockItemStatus.getString("sent_date"),stockItemStatus.getString("qty_received"),stockItemStatus.getString("qty_sent"),stockItemStatus.getString("challani_no"));
                        stockItem.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                stockin();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void stockin() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.STOCKIN_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"stockin" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject stockInItem = (JSONObject) response.get(i);
                        if(i==0){
                            if(stockInItem.getString("status").equals("empty")){

                            }else if (stockInItem.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(stockInItem.getString("status").equals("success")){
                                StockIn.deleteAll(StockIn.class);
                            }
                        }
                        StockIn stockIn = new StockIn(stockInItem.getString("id"),stockInItem.getString("project_id"),stockInItem.getString("item_name"),stockInItem.getString("sent_date"),stockInItem.getString("qty_received"),stockInItem.getString("challani_no"));
                        stockIn.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                stockout();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void stockout() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.STOCKOUT_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"stockout" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject stockOutItem = (JSONObject) response.get(i);
                        if(i==0){
                            if(stockOutItem.getString("status").equals("empty")){

                            }else if (stockOutItem.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(stockOutItem.getString("status").equals("success")){
                                StockOut.deleteAll(StockOut.class);
                            }
                        }
                        StockOut stockOut = new StockOut(stockOutItem.getString("id"),stockOutItem.getString("project_id"),stockOutItem.getString("name"),stockOutItem.getString("sent_date"),stockOutItem.getString("current_stock"));
                        stockOut.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loadItems();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void loadItems() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.POST, Constants.BASE_URL+Constants.ITEMS_URL,new JSONObject(params) , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"items" +response.toString());
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject itemsJSON = (JSONObject) response.get(i);
                        if(i==0){
                            if(itemsJSON.getString("status").equals("empty")){

                            }else if (itemsJSON.getString("status").equals("token_mismatch")){
                                clearSession();

                            }else if(itemsJSON.getString("status").equals("success")){
                                Item.deleteAll(Item.class);
                            }
                        }
                        Item item = new Item(itemsJSON.getString("id"),itemsJSON.getString("project_id"),itemsJSON.getString("name"),itemsJSON.getString("type_id"),itemsJSON.getString("unit_type"));
                        item.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sendBroadcast(intent);
                stopSelf();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                stopSelf();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    private void clearSession() {
        Log.d("TAG","Clear Session Called");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        refreshDB();
        stopSelf();
    }

    public  int maxPID(){
        Project p = Project.last(Project.class);
        if(p!=null){
            Log.d("Project Data is","project data "+p.getpId());
            int m = Integer.parseInt(p.getpId());
            return m;
        }else{
            return 0;
        }
    }
    public int maxSID(){
        SubTask p = SubTask.last(SubTask.class);
        if(p!=null){
            Log.d("Project Data is","project data "+p.gettId());
            int m = Integer.parseInt(p.gettId());
            return m;
        }else{
            return 0;
        }
    }

    private void notifyUser(int i) {
        if(i==1){
            Log.d("Notification","Project notify is called");
            Intent intent = new Intent(this, Projects.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Notification n  = new Notification.Builder(this)
                    .setContentTitle("New Project Has Been Assigned")
                    .setContentText("New Project")
                    .setSmallIcon(R.drawable.man)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.man, "Call", pIntent)
                    .addAction(R.drawable.man, "More", pIntent)
                    .addAction(R.drawable.man, "And more", pIntent).build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);
        }else if(i==2){
            Log.d("Notification","Task notify is called");
            Intent intent = new Intent(this, Project.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Notification n  = new Notification.Builder(this)
                    .setContentTitle("New Task Has Been Assigned")
                    .setContentText("New Task")
                    .setSmallIcon(R.drawable.man)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.man, "Call", pIntent)
                    .addAction(R.drawable.man, "More", pIntent)
                    .addAction(R.drawable.man, "And more", pIntent).build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);
        }
        return;
    }

    private void refreshDB() {
        //clear all table
        AddStockOut.deleteAll(AddStockOut.class);
        AddTaskRecord.deleteAll(AddTaskRecord.class);
        ItemReceivedReq.deleteAll(ItemReceivedReq.class);
        ItemReceivedSent.deleteAll(ItemReceivedSent.class);
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
