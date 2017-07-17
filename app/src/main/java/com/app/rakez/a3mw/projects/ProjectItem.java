package com.app.rakez.a3mw.projects;

/**
 * Created by RAKEZ on 6/30/2017.
 */

public class ProjectItem {
    private String projectName;
    private String pId;
    private String status;
    private String designer;
    private String startDate;
    private String endDate;

    public ProjectItem(String projectName, String pId, String status, String designer, String startDate, String endDate) {
        this.projectName = projectName;
        this.pId = pId;
        this.status = status;
        this.designer = designer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getpId() {
        return pId;
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
