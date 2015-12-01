package com.gonza.inventariar.inventariar.Elements;


import com.orm.SugarRecord;

public class Item extends SugarRecord<Item> {
    private String inventoryCode;
    private Localization localization;
    private String name;
    private String category;
    private String brand;
    private String barCode;
    private String description;
    private String value;
    private int pictures;

    public Item(){
    }

    public Item(String inventoryCode,Localization localization, String name, String category) {
        this.inventoryCode = inventoryCode;
        this.name = name;
        this.category = category;
        this.localization = localization;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public Localization getLocalization(){
        return localization;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public int getPictures(){
        return pictures;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public void setPictures(int pictures){
        this.pictures = pictures;
    }
}
