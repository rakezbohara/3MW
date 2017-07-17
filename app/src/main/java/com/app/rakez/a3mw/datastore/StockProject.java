package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class StockProject extends SugarRecord {
    String pId;
    String projectName;
    String status;
    String designer;
    String startDate;
    String endDate;

    public StockProject() {
    }

    public StockProject(String pId, String projectName, String status, String designer, String startDate, String endDate) {
        this.pId = pId;
        this.projectName = projectName;
        this.status = status;
        this.designer = designer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getpId() {
        return pId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStatus() {
        return status;
    }

    public String getDesigner() {
        return designer;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
