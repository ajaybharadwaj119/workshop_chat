package com.example.firebasechat.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "hbschit.db";
    public static final int DB_VERSION = 1;
    Context context;


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table if not exists cart (pro_id text, pro_name text, pro_spec text,pro_image text, qty INTEGER, price double,selling_price double, alert_message text)";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS cart");


        onCreate(db);
    }
}
