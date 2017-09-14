package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class StockProject extends SugarRecord {
    String pId;
    String projectName;
    String clientName;

    public StockProject() {
    }

    public StockProject(String pId, String projectName, String clientName) {
        this.pId = pId;
        this.projectName = projectName;
        this.clientName = clientName;
    }

    public String getpId() {
        return pId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getClientName() {
        return clientName;
    }
}
