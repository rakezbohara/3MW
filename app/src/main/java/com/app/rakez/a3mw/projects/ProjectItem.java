package com.app.rakez.a3mw.projects;

/**
 * Created by RAKEZ on 6/30/2017.
 */

public class ProjectItem {
    private String projectName;
    private String pId;
    private String clientName;
    private String progressPercentage;

    public ProjectItem(String projectName, String pId, String clientName, String progressPercentage) {
        this.projectName = projectName;
        this.pId = pId;
        this.clientName = clientName;
        this.progressPercentage = progressPercentage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(String progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
