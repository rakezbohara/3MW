package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class TaskRecord extends SugarRecord {
    String trId;
    String sub_tasks_id;
    String fabrication;
    String erection;
    String c_fabrication;
    String c_erection;
    String date;

    public TaskRecord() {
    }

    public TaskRecord(String trId, String sub_tasks_id, String fabrication, String erection, String c_fabrication, String c_erection, String date) {
        this.trId = trId;
        this.sub_tasks_id = sub_tasks_id;
        this.fabrication = fabrication;
        this.erection = erection;
        this.c_fabrication = c_fabrication;
        this.c_erection = c_erection;
        this.date = date;
    }

    public String getTrId() {
        return trId;
    }

    public String getSub_tasks_id() {
        return sub_tasks_id;
    }

    public String getFabrication() {
        return fabrication;
    }

    public String getErection() {
        return erection;
    }

    public String getC_fabrication() {
        return c_fabrication;
    }

    public String getC_erection() {
        return c_erection;
    }

    public String getDate() {
        return date;
    }
}
