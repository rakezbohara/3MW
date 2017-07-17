package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/14/2017.
 */

public class AddTaskRecord extends SugarRecord{
    String userId;
    String token;
    String sId;
    String fab;
    String erc;

    public AddTaskRecord() {
    }

    public AddTaskRecord(String userId, String token, String sId, String fab, String erc) {
        this.userId = userId;
        this.token = token;
        this.sId = sId;
        this.fab = fab;
        this.erc = erc;
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

    public String getFab() {
        return fab;
    }

    public String getErc() {
        return erc;
    }
}
