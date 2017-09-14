package com.app.rakez.a3mw.datastore;


import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/6/2017.
 */

public class Project extends SugarRecord {

    String pId;
    String projectName;
    String clientName;
    String progressPercentage;

    public Project() {

    }

    public Project(String pId, String projectName, String clientName, String progressPercentage) {
        this.pId = pId;
        this.projectName = projectName;
        this.clientName = clientName;
        this.progressPercentage = progressPercentage;
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

    public String getProgressPercentage() {
        return progressPercentage;
    }
}
