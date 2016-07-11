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

public class MainActivity extends AppCompatActivity {

    public static MainActivity ma;
    private String[] daftar = {"Wisata Candi", "Wisata Pantai", "Wisata Alam"};
    private ListView listKamus;
    private Cursor cursor;
    private SQLHelper dbHelper;
    private Button buttonTambah;

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
        ma = this;

        dbHelper = new SQLHelper(this);

        listKamus = (ListView) findViewById(R.id.listKamus);

        buttonTambah = (Button) findViewById(R.id.buttonAdd);
        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        //refreshList();

        dbHelper = new SQLHelper(this);

        try {
            dbHelper.createDataBase();
        }
        catch (Exception ioe) {
            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
        }
        read_data();

    }

    public void read_data()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM kata",null);

        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++)
        {
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
}
