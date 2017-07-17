package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class StockIn extends SugarRecord {
    String itemId;
    String pId;
    String itemName;
    String sentDate;
    String qtyReceived;
    String challaniNo;

    public StockIn() {
    }

    public StockIn(String itemId, String pId, String itemName, String sentDate, String qtyReceived, String challaniNo) {
        this.itemId = itemId;
        this.pId = pId;
        this.itemName = itemName;
        this.sentDate = sentDate;
        this.qtyReceived = qtyReceived;
        this.challaniNo = challaniNo;
    }

    public String getItemId() {
        return itemId;
    }

    public String getpId() {
        return pId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getQtyReceived() {
        return qtyReceived;
    }

    public String getChallaniNo() {
        return challaniNo;
    }
}
