package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/13/2017.
 */

public class StockOut extends SugarRecord {
    String itemId;
    String pId;
    String itemName;
    String sentDate;
    String current_stock;

    public StockOut() {
    }

    public StockOut(String itemId, String pId, String itemName, String sentDate, String current_stock) {
        this.itemId = itemId;
        this.pId = pId;
        this.itemName = itemName;
        this.sentDate = sentDate;
        this.current_stock = current_stock;
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

    public String getCurrent_stock() {
        return current_stock;
    }
}
