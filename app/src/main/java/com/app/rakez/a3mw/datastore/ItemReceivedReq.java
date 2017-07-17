package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/15/2017.
 */

public class ItemReceivedReq extends SugarRecord {
    String userId;
    String token;
    String itemId;
    String pId;
    String quantity;
    String challaniNo;

    public ItemReceivedReq() {
    }

    public ItemReceivedReq(String userId, String token, String itemId, String pId, String quantity, String challaniNo) {
        this.userId = userId;
        this.token = token;
        this.itemId = itemId;
        this.pId = pId;
        this.quantity = quantity;
        this.challaniNo = challaniNo;
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

    public String getChallaniNo() {
        return challaniNo;
    }
}
