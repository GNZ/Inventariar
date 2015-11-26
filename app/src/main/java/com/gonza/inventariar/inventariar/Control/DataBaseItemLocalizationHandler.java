package com.gonza.inventariar.inventariar.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gonza.inventariar.inventariar.Elements.Item;

public class DataBaseItemLocalizationHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LocationItem";

    // tables name
    private static final String TABLE_LOCATION = "locations";
    private static final String TABLE_ITEM = "items";

    // Location Table Columns names
    private static final String KEY_INVENTORY_CODE_LOCATION = "inventory_code";

    // Items Table Columns names
    private static final String KEY_INVENTORY_CODE_ITEM = "inventory_code";
    private static final String KEY_LOCALIZATION_ITEM = "localization";
    private static final String KEY_NAME_ITEM = "name";
    private static final String KEY_CATEGORY_ITEM = "category";
    private static final String KEY_BRAND_ITEM = "brand";
    private static final String KEY_BARCODE_ITEM = "barcode";
    private static final String KEY_PICTURES_ITEM = "pictures";
    private static final String KEY_DESCRIPTION_ITEM = "description";


    public DataBaseItemLocalizationHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String locationTable = "CREATE TABLE "+TABLE_LOCATION +"("+
                KEY_INVENTORY_CODE_LOCATION + " TEXT PRIMARY KEY)";

        String itemTable = "CREATE TABLE "+TABLE_ITEM +"("+
                KEY_INVENTORY_CODE_ITEM + " TEXT PRIMARY KEY, "+KEY_LOCALIZATION_ITEM+" TEXT NOT NULL, "+
                KEY_NAME_ITEM+" TEXT NOT NULL, "+KEY_CATEGORY_ITEM+" TEXT NOT NULL, "+KEY_BRAND_ITEM+
                " TEXT, "+KEY_BARCODE_ITEM+" TEXT, "+KEY_PICTURES_ITEM+" TEXT NOT NULL, "+KEY_DESCRIPTION_ITEM+
                " TEXT)";
        db.execSQL(locationTable);
        db.execSQL(itemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);

        // Create tables again
        onCreate(db);
    }

    public void addLocation(String inventoryCode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues location = new ContentValues();

        location.put(KEY_INVENTORY_CODE_LOCATION,inventoryCode);

        db.insert(TABLE_LOCATION,null,location);
        db.close();
    }

    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues itemValue = new ContentValues();

        itemValue.put(KEY_INVENTORY_CODE_ITEM,item.getInventoryCode());
        itemValue.put(KEY_LOCALIZATION_ITEM,item.getLocalization());
        itemValue.put(KEY_NAME_ITEM,item.getName());
        itemValue.put(KEY_CATEGORY_ITEM,item.getCategory());
        if (item.getBrand() !=null) itemValue.put(KEY_BRAND_ITEM,item.getBrand());
        if (item.getBarCode() != null) itemValue.put(KEY_BARCODE_ITEM,item.getBarCode());
        itemValue.put(KEY_PICTURES_ITEM,item.getPictures());
        if (item.getDescription() != null) itemValue.put(KEY_DESCRIPTION_ITEM,item.getDescription());

        db.insert(TABLE_ITEM,null,itemValue);
        db.close();
    }

    public Item getItem(String inventoryCode){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEM, new String[] { KEY_INVENTORY_CODE_ITEM,
                        KEY_LOCALIZATION_ITEM, KEY_NAME_ITEM, KEY_CATEGORY_ITEM, KEY_BRAND_ITEM, KEY_BARCODE_ITEM,
                        KEY_PICTURES_ITEM, KEY_DESCRIPTION_ITEM}, KEY_INVENTORY_CODE_ITEM + "=?",
                new String[] { inventoryCode }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Item item = new Item(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        if (!cursor.getString(4).equals("")) item.setBrand(cursor.getString(4));
        if (!cursor.getString(5).equals("")) item.setBarCode(cursor.getString(5));
        item.setPictures(Integer.parseInt(cursor.getString(6)));
        if (!cursor.getString(7).equals("")) item.setDescription(cursor.getString(7));

        return item;
    }



}
