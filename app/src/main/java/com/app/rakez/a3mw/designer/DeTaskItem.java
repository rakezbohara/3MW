package com.app.rakez.a3mw.designer;

/**
 * Created by RAKEZ on 7/16/2017.
 */

public class DeTaskItem {
    private String tId;
    private String taskName;
    private String totalRequirement;
    private String weight;
    private String unit;

    public DeTaskItem() {
    }

    public DeTaskItem(String tId, String taskName, String totalRequirement, String weight, String unit) {
        this.tId = tId;
        this.taskName = taskName;
        this.totalRequirement = totalRequirement;
        this.weight = weight;
        this.unit = unit;
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

    public String getWeight() {
        return weight;
    }

    public String getUnit() {
        return unit;
    }
}
