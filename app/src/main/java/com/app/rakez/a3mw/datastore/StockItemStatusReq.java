package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class StockItemStatusReq extends SugarRecord {
    String itemId;
    String pId;
    String itemName;
    String itemStatus;
    String req_date;
    String quantity;
    String qty_received;
    String qty_sent;
    String challaniNo;

    public StockItemStatusReq() {
    }

    public StockItemStatusReq(String itemId, String pId, String itemName, String itemStatus, String req_date, String quantity, String qty_received, String qty_sent, String challaniNo) {
        this.itemId = itemId;
        this.pId = pId;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.req_date = req_date;
        this.quantity = quantity;
        this.qty_received = qty_received;
        this.qty_sent = qty_sent;
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

    public String getItemStatus() {
        return itemStatus;
    }

    public String getReq_date() {
        return req_date;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQty_received() {
        return qty_received;
    }

    public String getQty_sent() {
        return qty_sent;
    }

    public String getChallaniNo() {
        return challaniNo;
    }
}
