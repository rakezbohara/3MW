package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/14/2017.
 */

public class NewStockRequest extends SugarRecord {
    String userId;
    String token;
    String itemId;
    String newItem;
    String pId;
    String quantity;
    String priority;
    String note;

    public NewStockRequest() {
    }

    public NewStockRequest(String userId, String token, String itemId, String newItem, String pId, String quantity, String priority, String note) {
        this.userId = userId;
        this.token = token;
        this.itemId = itemId;
        this.newItem = newItem;
        this.pId = pId;
        this.quantity = quantity;
        this.priority = priority;
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getItemId() {
        return itemId;
    }

    public String getNewItem() {
        return newItem;
    }

    public String getpId() {
        return pId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPriority() {
        return priority;
    }

    public String getNote() {
        return note;
    }
}
