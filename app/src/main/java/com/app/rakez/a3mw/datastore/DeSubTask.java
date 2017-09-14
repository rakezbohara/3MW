package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/16/2017.
 */

public class DeSubTask extends SugarRecord {
    String tId;
    String pId;
    String job;
    String diameter;
    String thickness;
    String total_requirement;
    String unit_weight;
    String unit;

    public DeSubTask() {
    }

    public DeSubTask(String tId, String pId, String job, String diameter, String thickness, String total_requirement, String unit_weight, String unit) {
        this.tId = tId;
        this.pId = pId;
        this.job = job;
        this.diameter = diameter;
        this.thickness = thickness;
        this.total_requirement = total_requirement;
        this.unit_weight = unit_weight;
        this.unit = unit;
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

    public String getUnit_weight() {
        return unit_weight;
    }

    public String getUnit() {
        return unit;
    }
}
