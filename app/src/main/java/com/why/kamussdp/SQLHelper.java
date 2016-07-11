package com.why.kamussdp;

/**
 * Created by sdp03 on 7/11/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//http://cariprogram.blogspot.com
//nuramijaya@gmail.com

public class SQLHelper extends SQLiteOpenHelper {

    // Table name
    public static final String TABLE = "kata";
    private static final String DATABASE_NAME = "kamus.db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/com.why.kamussdp/databases/";
    private Context myContext;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  /*
        String sql = "create table kata( id integer primary key autoincrement, " +
                "inggris text null, " +
                "indonesia text null, " +
                "keterangan text null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO kata (id, inggris, indonesia, keterangan) VALUES (1, 'City', 'Kota', 'Daerah Pusat Pemukiman');";
        db.execSQL(sql);
        sql = "INSERT INTO kata (id, inggris, indonesia, keterangan) VALUES (2, 'Car', 'Mobil', 'Kendaraan Roda Empat');";
        db.execSQL(sql);
*/
    }

    public void createDataBase() throws IOException {
        if (DataBaseisExist()) {
            //do nothing - database already exist
            Toast.makeText(myContext, "Database Sudah Ada", Toast.LENGTH_LONG).show();
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
                Toast.makeText(myContext, "Database Berhasil Diimport Dari Assets", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    private boolean DataBaseisExist() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        if (checkDB != null) return true;
        else return false;
    }

    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}