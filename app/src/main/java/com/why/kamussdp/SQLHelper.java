package com.why.kamussdp;

/**
 * Created by sdp03 on 7/11/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//http://cariprogram.blogspot.com
//nuramijaya@gmail.com

public class SQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kamus.db";
    private static final int DATABASE_VERSION = 1;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table kata( id integer primary key autoincrement, " +
                "inggris text null, " +
                "indonesia text null, " +
                "keterangan text null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO kota (id, inggris, indonesia, keterangan) VALUES (1, 'City', 'Kota', 'Daerah Pusat Pemukiman');";
        db.execSQL(sql);
        sql = "INSERT INTO kota (id, inggris, indonesia, keterangan) VALUES (2, 'Car', 'Mobil', 'Kendaraan Roda Empat');";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


    }

}