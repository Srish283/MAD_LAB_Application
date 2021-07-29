package com.srishti.texttospeechconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText tts;
    Button bn,bn1;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts=(EditText)findViewById(R.id.tts);
        bn=(Button)findViewById(R.id.bn);
        bn1=(Button)findViewById(R.id.bn1);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setText("");
            }
        });
    }
    public void textToSpeech(View view){
        String text=tts.getText().toString();
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
}