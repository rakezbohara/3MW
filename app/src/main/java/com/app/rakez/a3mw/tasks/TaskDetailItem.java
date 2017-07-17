package com.app.rakez.a3mw.tasks;

/**
 * Created by RAKEZ on 7/2/2017.
 */

public class TaskDetailItem {
    private String trId;
    private String date;
    private String fabrication;
    private String cFabrication;
    private String erection;
    private String cErection;

    public TaskDetailItem(String trId, String date, String fabrication, String cFabrication, String erection, String cErection) {
        this.trId = trId;
        this.date = date;
        this.fabrication = fabrication;
        this.cFabrication = cFabrication;
        this.erection = erection;
        this.cErection = cErection;
    }

    public String getTrId() {
        return trId;
    }

    public String getDate() {
        return date;
    }

    public String getFabrication() {
        return fabrication;
    }

    public String getcFabrication() {
        return cFabrication;
    }

    public String getErection() {
        return erection;
    }

    public String getcErection() {
        return cErection;
    }
}
