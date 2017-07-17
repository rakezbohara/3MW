package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/16/2017.
 */

public class AddStockOut extends SugarRecord {
    String userId;
    String token;
    String itemId;
    String pId;
    String quantity;

    public AddStockOut() {
    }

    public AddStockOut(String userId, String token, String itemId, String pId, String quantity) {
        this.userId = userId;
        this.token = token;
        this.itemId = itemId;
        this.pId = pId;
        this.quantity = quantity;
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

    public String getpId() {
        return pId;
    }

    public String getQuantity() {
        return quantity;
    }
}
