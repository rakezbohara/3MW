package com.app.rakez.a3mw.Stock.ItemStatus;

/**
 * Created by RAKEZ on 7/4/2017.
 */

public class ItemSentAdminItem {

    private String itemId;
    private String itemName;
    private String date;
    private String challaniNo;
    private String qtySent;
    private String qtyReceived;
    private String status;

    public ItemSentAdminItem(String itemId, String itemName, String date, String challaniNo, String qtySent, String qtyReceived, String status) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.date = date;
        this.challaniNo = challaniNo;
        this.qtySent = qtySent;
        this.qtyReceived = qtyReceived;
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDate() {
        return date;
    }

    public String getChallaniNo() {
        return challaniNo;
    }

    public String getQtySent() {
        return qtySent;
    }

    public String getQtyReceived() {
        return qtyReceived;
    }

    public String getStatus() {
        return status;
    }
}
