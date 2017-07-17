package com.app.rakez.a3mw.Stock.ItemStatus;

/**
 * Created by RAKEZ on 7/3/2017.
 */

public class ItemRequestStatusItem {
    private String itemId;
    private String itemName;
    private String date;
    private String challaniNo;
    private String totalQty;
    private String qtySent;
    private String qtyReceived;
    private String status;

    public ItemRequestStatusItem(String itemId, String itemName, String date, String challaniNo, String totalQty, String qtySent, String qtyReceived, String status) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.date = date;
        this.challaniNo = challaniNo;
        this.totalQty = totalQty;
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

    public String getTotalQty() {
        return totalQty;
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
