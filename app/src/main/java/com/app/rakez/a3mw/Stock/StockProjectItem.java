package com.app.rakez.a3mw.Stock;

/**
 * Created by RAKEZ on 7/3/2017.
 */

public class StockProjectItem {
    private String pId;
    private String projectName;
    private String status;
    private String designer;

    public StockProjectItem(String pId, String projectName, String status, String designer) {
        this.pId = pId;
        this.projectName = projectName;
        this.status = status;
        this.designer = designer;
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
}
