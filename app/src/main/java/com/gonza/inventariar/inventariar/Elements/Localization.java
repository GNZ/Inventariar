package com.gonza.inventariar.inventariar.Elements;

import com.orm.SugarRecord;


public class Localization extends SugarRecord<Localization> {
    private String inventoryCode;

    public Localization() {
    }

    public Localization(String inventoryCode){
        this.inventoryCode = inventoryCode;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }
}
