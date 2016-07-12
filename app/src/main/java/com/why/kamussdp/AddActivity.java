package com.why.kamussdp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText etInggris, etIndonesia, etKeterangan;
    private Button btTambahSimpan;

    private SQLHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new SQLHelper(this);

        etInggris = (EditText) findViewById(R.id.etInggris);
        etIndonesia = (EditText) findViewById(R.id.etIndonesia);
        etKeterangan = (EditText) findViewById(R.id.etKeterangan);
        btTambahSimpan = (Button) findViewById(R.id.buttonTambahSimpan);
        btTambahSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into kata (inggris, indonesia, keterangan) values('" + etInggris.getText().toString() +
                        "','" + etIndonesia.getText().toString() + "','" + etKeterangan.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                MainActivity.ma.refreshList();
                finish();
            }
        });
    }
}
