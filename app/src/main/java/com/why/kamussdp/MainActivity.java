package com.why.kamussdp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DATABASE_NAME = "kamus.db";
    public static MainActivity ma;
    private static String DB_PATH = "/data/data/com.why.kamussdp/databases/";
    private String[] daftar = {"Wisata Candi", "Wisata Pantai", "Wisata Alam"};
    private ListView listKamus;
    private Cursor cursor;
    private SQLHelper dbHelper;
    private Button buttonTambah, buttonBaca, buttonBackup, buttonRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listKamus = (ListView) findViewById(R.id.listKamus);
        buttonTambah = (Button) findViewById(R.id.buttonAdd);
        buttonTambah.setOnClickListener(this);
        buttonBaca = (Button) findViewById(R.id.btReadData);
        buttonBaca.setOnClickListener(this);
        buttonBackup = (Button) findViewById(R.id.btBackup);
        buttonBackup.setOnClickListener(this);
        buttonRestore = (Button) findViewById(R.id.btRestore);
        buttonRestore.setOnClickListener(this);

        ma = this;
        dbHelper = new SQLHelper(this);
        dbHelper = new SQLHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (Exception ioe) {
            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
        }
        read_data();
        insertIntoDb();

        refreshList();
    }

    private void insertIntoDb() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into kata (inggris, indonesia, keterangan) values('one','satu','angka')");
        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
        read_data();
    }

    public void read_data() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kata", null);
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            Toast.makeText(getApplicationContext(), cursor.getString(1).toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void refreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kata", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        listKamus.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ((ArrayAdapter) listKamus.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonTambah.getId()) {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
        } else if (v.getId() == buttonBaca.getId()) {
            read_data();
        } else if (v.getId() == buttonBackup.getId()) {
            try {
                File file = new File(DB_PATH + DATABASE_NAME); //Uri.toString());
                //FileInputStream myInput = FileInputStream(file); // myContext.getAssets().open(DB_NAME);
                FileInputStream myInput;

                myInput = new FileInputStream(file);

                // Path to the just created empty db
                String outFileName = "/sdcard/kamus.db"; // DB_PATH + DB_NAME;

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
                Toast.makeText(getApplicationContext(), "Berhasil Backup", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        } else if (v.getId() == buttonRestore.getId()) {
            // TODO Auto-generated method stub
            try {
                File file = new File("/sdcard/kamus.db"); //Uri.toString());
                //FileInputStream myInput = FileInputStream(file); // myContext.getAssets().open(DB_NAME);
                FileInputStream myInput;

                myInput = new FileInputStream(file);


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
                Toast.makeText(getApplicationContext(), "Berhasil Restore", Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
