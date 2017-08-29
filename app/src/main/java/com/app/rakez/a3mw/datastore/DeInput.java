package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/17/2017.
 */

public class DeInput extends SugarRecord {
    String userId;
    String token;
    String sId;
    String weight;
    String unit;

    public DeInput() {
    }

    public DeInput(String userId, String token, String sId, String weight, String unit) {
        this.userId = userId;
        this.token = token;
        this.sId = sId;
        this.weight = weight;
        this.unit = unit;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getsId() {
        return sId;
    }

    public String getWeight() {
        return weight;
    }

    public String getUnit() {
        return unit;
    }
}
