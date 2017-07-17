package com.app.rakez.a3mw.tasks;

/**
 * Created by RAKEZ on 7/2/2017.
 */

public class TaskItem {
    private String tId;
    private String taskName;
    private String totalRequirement;
    private String fabrication;
    private String erection;

    public TaskItem(String tId, String taskName, String totalRequirement, String fabrication, String erection) {
        this.tId = tId;
        this.taskName = taskName;
        this.totalRequirement = totalRequirement;
        this.fabrication = fabrication;
        this.erection = erection;
    }

    public String gettId() {
        return tId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTotalRequirement() {
        return totalRequirement;
    }

    public String getFabrication() {
        return fabrication;
    }

    public String getErection() {
        return erection;
    }
}
