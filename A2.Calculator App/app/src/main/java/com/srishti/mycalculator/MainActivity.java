package com.srishti.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,buttondot,buttonsp,
            buttoneq,buttonadd,buttonsub,buttonmul,buttondiv;

     EditText display;
     float value1,value2;
     boolean cadd,csub,cmul,cdiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById((R.id.button1));
        button2=(Button)findViewById((R.id.button2));
        button3=(Button)findViewById((R.id.button3));
        button4=(Button)findViewById((R.id.button4));
        button5=(Button)findViewById((R.id.button5));
        button6=(Button)findViewById((R.id.button6));
        button7=(Button)findViewById((R.id.button7));
        button8=(Button)findViewById((R.id.button8));
        button9=(Button)findViewById((R.id.button9));
        button10=(Button)findViewById((R.id.button10));
        buttonadd=(Button)findViewById((R.id.buttonadd));
        buttonsub=(Button)findViewById((R.id.buttonsub));
        buttonmul=(Button)findViewById((R.id.buttonmul));
        buttondiv=(Button)findViewById((R.id.buttondiv));
        buttoneq=(Button)findViewById((R.id.buttoneq));
        buttonsp=(Button)findViewById((R.id.buttonsp));
        buttondot=(Button)findViewById((R.id.buttondot));
        display=(EditText)findViewById((R.id.display));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"9");
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+"0");
            }
        });
        buttondot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(display.getText()+".");
            }
        });
        buttonsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(" ");
            }
        });
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value1=Float.parseFloat(display.getText()+"");
                cadd=true;
                display.setText(null);
            }
        });
        buttonsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value1=Float.parseFloat(display.getText()+"");
                csub=true;
                display.setText(null);
            }
        });
        buttonmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value1=Float.parseFloat(display.getText()+"");
                cmul=true;
                display.setText(null);
            }
        });
        buttondiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value1=Float.parseFloat(display.getText()+"");
                cdiv=true;
                display.setText(null);
            }
        });
        buttoneq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value2=Float.parseFloat((display.getText()+""));
                if(cadd){
                    display.setText((value1+value2+""));
                    cadd=false;
                }
                if(csub){
                    display.setText((value1-value2+""));
                    csub=false;
                }
                if(cmul){
                    display.setText((value1*value2+""));
                    cmul=false;
                }
                if(cdiv){
                    display.setText((value1/value2+""));
                    cdiv=false;
                }
            }
        });
    }
}