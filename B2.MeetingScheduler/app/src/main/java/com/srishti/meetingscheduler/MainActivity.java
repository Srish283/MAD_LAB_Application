package com.srishti.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText magenda, mdate, mtime;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    AlarmManager alarmManager;
    int uday,umonth,uyear,uhour,umin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        magenda=(EditText)findViewById(R.id.mname);
        mdate=(EditText)findViewById(R.id.mdate);
        mtime=(EditText)findViewById(R.id.mtime);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        mdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal1=Calendar.getInstance();
                int cyear=cal1.get(Calendar.YEAR);
                int cmonth=cal1.get(Calendar.MONTH);
                int cday=cal1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mdate.setText(dayOfMonth+"/"+(month+1)+"/"+year);  /* month +1 as months are numbered starting from 0 */

                    }
                },cyear,cmonth,cday);
                datePickerDialog.show();

            }
        });

        mtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal2=Calendar.getInstance();
                int hour=cal2.get(Calendar.HOUR_OF_DAY);
                int minute=cal2.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mtime.setText(hourOfDay+":"+minute);
                    }
                },hour,minute,false);
                timePickerDialog.show();

            }
        });
    }
    public void onClickAdd(View v){
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.AGENDA,magenda.getText().toString());
        values.put(MyContentProvider.DATE,mdate.getText().toString());
        values.put(MyContentProvider.TIME,mtime.getText().toString());


        Uri uri= getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
        magenda.setText("");
        mdate.setText("");
        mtime.setText("");
        Toast.makeText(getBaseContext(), "Record Added "+ uri.toString(), Toast.LENGTH_LONG).show();
        addAlarm();
    }
    public void onSearch(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);

    }
    public void addAlarm()
    {
        Calendar cal=Calendar.getInstance();
        cal.set(uyear,umonth,uday,uhour,umin);
        Intent intent=new Intent(MainActivity.this,MyAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }

}