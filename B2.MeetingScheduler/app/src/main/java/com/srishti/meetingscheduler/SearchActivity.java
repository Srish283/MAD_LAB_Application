package com.srishti.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    TextView textv;
    EditText key;
    DatePickerDialog datePickerDialog1;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        key=(EditText)findViewById(R.id.key);
        textv=(TextView)findViewById(R.id.textv2);


        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

    }
    public void retrieve() {
        // Retrieve meeting records
        String URL = "content://com.srishti.meetingscheduler.MyContentProvider";
        String txt;

        Uri meetinguri = Uri.parse(URL);
        String searchQuery = "date like '%" + key.getText().toString() + "%' ";


        Cursor c = getContentResolver().query(meetinguri, null, searchQuery, null, "time DESC");


        if (c.moveToFirst()) {
            textv.setText("\n__________________________________\n");
            do{
                textv.setText(textv.getText()+
                        "\n"+  " ID          :-   "+c.getString(c.getColumnIndex(MyContentProvider.ID)) +
                        "\n" + " AGENDA :-   " +c.getString(c.getColumnIndex( MyContentProvider.AGENDA))+
                        "\n" + " DATE     :-   "+c.getString(c.getColumnIndex( MyContentProvider.DATE)) +
                        "\n" + " TIME     :-   "+c.getString(c.getColumnIndex(MyContentProvider.TIME))+"\n");
            } while (c.moveToNext());
        }
        else {
            Toast.makeText(this, "No events for the day!!", Toast.LENGTH_SHORT).show();
            textv.setText("      No events for the day!!");
        }
        txt=textv.getText().toString();
        Toast.makeText(SearchActivity.this,txt,Toast.LENGTH_LONG).show();
        textToSpeech.speak(txt,TextToSpeech.QUEUE_FLUSH,null);

    }

    public void findDate(View v1)
    {



        final Calendar c1=Calendar.getInstance();
        int cyear=c1.get(Calendar.YEAR);
        int cmonth=c1.get(Calendar.MONTH);
        int cday=c1.get(Calendar.DAY_OF_MONTH);
        datePickerDialog1=new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                key.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                retrieve();
            }
        },cyear,cmonth,cday);
        datePickerDialog1.show();


    }

}