package com.srishti.medicinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mname,mdate,mtime;
    Button insert,showlist;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    AlarmManager alarmManager;
    DatabaseHelper myDB;
    Spinner s;
    int uday,umonth,uyear,uhour,umin;
    boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mname = (EditText) findViewById(R.id.mname);
        mdate = (EditText) findViewById(R.id.mdate);
        mtime = (EditText) findViewById(R.id.mtime);
        insert = (Button) findViewById(R.id.insert);
        showlist = (Button) findViewById(R.id.showlist);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        showlist.setOnClickListener(this);
        insert.setOnClickListener(this);
        myDB = new DatabaseHelper(this);
        s=(Spinner)findViewById(R.id.spin);
        addDate();
        addTime();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String date,time;
                date=mdate.getText().toString();
                time=mtime.getText().toString();
                String dateParts[] = date.split("/");

                String day = dateParts[0];
                String month = dateParts[1];
                String year = dateParts[2];
                int d=Integer.parseInt(day);
                int m=Integer.parseInt(month);
                int y=Integer.parseInt(year);
                String myItem= adapterView.getSelectedItem().toString();
                if(myItem =="Everyday"){
                    while(true){
                        if(d==31 && (m==1 || m==3 || m==5 || m==7 || m==8 || m==10)){
                            d=1;
                            m+=1;
                        }
                        else if(d==31 && m==12){
                            d=1;
                            m=1;
                            y+=1;
                        }
                        else if(d==30 && (m==4||m==6||m==9||m==11)){
                            d=1;
                            m+=1;
                        }
                        else if((d==28 || d==29)&& m==2){
                            d=1;
                            m+=1;
                        }
                        String mydate=String.valueOf(d)+'/'+String.valueOf(m)+'/'+String.valueOf(y);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.insert){
            String name,date,time;
            name=mname.getText().toString();
            date=mdate.getText().toString();
            time=mtime.getText().toString();
            status=myDB.insertData(name,date,time);
            if(status){
                Toast.makeText(MainActivity.this,"Record Added Successfully",Toast.LENGTH_SHORT).show();
                mname.setText("");
                mdate.setText("");
                mtime.setText("");
                addAlarm();

            }
            else{
                Toast.makeText(MainActivity.this,"Record Not Added",Toast.LENGTH_SHORT).show();
            }

        }
        else if(v.getId()==R.id.showlist)
        {
            Intent intent=new Intent(this,ListActivity.class);
            startActivity(intent);

        }
    }

    public  void  addDate(){

        mdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c1=Calendar.getInstance();

                int cyear=c1.get(Calendar.YEAR);
                int cmonth=c1.get(Calendar.MONTH);
                int cday=c1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        uyear=year;
                        umonth=month;
                        uday=dayOfMonth;
                        mdate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },cyear,cmonth,cday);
                datePickerDialog.show();
            }
        });
    }



    public  void  addTime(){
        mtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2=Calendar.getInstance();
                int chour=c2.get(Calendar.HOUR_OF_DAY);
                int cmin=c2.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mtime.setText(hourOfDay+":"+minute);
                        uhour=hourOfDay;
                        umin=minute;
                    }
                },chour,cmin,false);
                timePickerDialog.show();

            }
        });

    }
    public void addAlarm()
    {
        Calendar cal=Calendar.getInstance();
        cal.set(uyear,umonth,uday,uhour,umin);
        Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }


}