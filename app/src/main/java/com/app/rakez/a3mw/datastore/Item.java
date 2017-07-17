package com.app.rakez.a3mw.datastore;

import com.orm.SugarRecord;

/**
 * Created by RAKEZ on 7/14/2017.
 */

public class Item extends SugarRecord {
    String itemId;
    String pId;
    String name;
    String type;
    String unit;

    public Item() {
    }

    public Item(String itemId, String pId, String name, String type, String unit) {
        this.itemId = itemId;
        this.pId = pId;
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public String getItemId() {
        return itemId;
    }

    public String getpId() {
        return pId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }
}
