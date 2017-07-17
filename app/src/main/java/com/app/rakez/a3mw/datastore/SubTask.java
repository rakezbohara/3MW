package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class SubTask extends SugarRecord {
    String tId;
    String pId;
    String job;
    String diameter;
    String thickness;
    String total_requirement;
    String c_fabrication;
    String c_erection;

    public SubTask() {
    }

    public SubTask(String tId, String pId, String job, String diameter, String thickness, String total_requirement, String c_fabrication, String c_erection) {
        this.tId = tId;
        this.pId = pId;
        this.job = job;
        this.diameter = diameter;
        this.thickness = thickness;
        this.total_requirement = total_requirement;
        this.c_fabrication = c_fabrication;
        this.c_erection = c_erection;
    }

    public String gettId() {
        return tId;
    }

    public String getpId() {
        return pId;
    }

    public String getJob() {
        return job;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getThickness() {
        return thickness;
    }

    public String getTotal_requirement() {
        return total_requirement;
    }

    public String getC_fabrication() {
        return c_fabrication;
    }

    public String getC_erection() {
        return c_erection;
    }
}
