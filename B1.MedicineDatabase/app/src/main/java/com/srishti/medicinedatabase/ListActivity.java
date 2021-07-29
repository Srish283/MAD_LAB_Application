package com.srishti.medicinedatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;


public class ListActivity extends AppCompatActivity {
    DatabaseHelper controllerdb = new DatabaseHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Time = new ArrayList<String>();
    ListView userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        userlist = (ListView)findViewById(R.id.ulist);
    }
    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }
    private void displayData() {
        db = controllerdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  Prescription_table",null);
        Id.clear();
        Name.clear();
        Date.clear();
        Time.clear();
        if (cursor.moveToFirst()) {
            do {
                Id.add(cursor.getString(cursor.getColumnIndex("ID")));
                Name.add(cursor.getString(cursor.getColumnIndex( "NAME")));
                Date.add(cursor.getString(cursor.getColumnIndex("DATE")));
                Time.add(cursor.getString(cursor.getColumnIndex("TIME")));
            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter(ListActivity.this,Id, Name,Date,Time);
        userlist.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}