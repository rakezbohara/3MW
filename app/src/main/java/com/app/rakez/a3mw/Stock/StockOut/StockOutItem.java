package com.app.rakez.a3mw.Stock.StockOut;

/**
 * Created by RAKEZ on 7/6/2017.
 */

public class StockOutItem {
    private String itemId;
    private String itemName;
    private String qty;

    public StockOutItem(String itemId, String itemName, String qty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.qty = qty;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQty() {
        return qty;
    }
}
