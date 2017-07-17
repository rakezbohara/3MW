package com.app.rakez.a3mw.Stock.StockIn;

/**
 * Created by RAKEZ on 7/4/2017.
 */

public class StockInItem {
    private String itemId;
    private String itemName;
    private String date;
    private String qtyReceived;
    private String challaniNo;

    public StockInItem(String itemId, String itemName, String date, String qtyReceived, String challaniNo) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.date = date;
        this.qtyReceived = qtyReceived;
        this.challaniNo = challaniNo;
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

    public String getQtyReceived() {
        return qtyReceived;
    }

    public String getChallaniNo() {
        return challaniNo;
    }
}
