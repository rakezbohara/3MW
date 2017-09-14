package com.app.rakez.a3mw.Stock;

/**
 * Created by RAKEZ on 7/3/2017.
 */

public class StockProjectItem {
    private String pId;
    private String projectName;
    private String clientName;

    public StockProjectItem(String pId, String projectName, String clientName) {
        this.pId = pId;
        this.projectName = projectName;
        this.clientName = clientName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
