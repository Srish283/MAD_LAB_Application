package com.srishti.validatingusers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {
    Button Login,cancelL;
    EditText emailL,etPasswordL,userL;
    TextView textv;
    int counter=3;
    String Email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=(Button)findViewById(R.id.btn1);
        cancelL=(Button)findViewById(R.id.btn2);
        emailL=(EditText)findViewById(R.id.emailL);
        etPasswordL=(EditText)findViewById(R.id.etPasswordL);
        textv=(TextView)findViewById(R.id.textv5);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            Email=bundle.getString("Email");
            password=bundle.getString("Password");
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lu=emailL.getText().toString();
                String lp=etPasswordL.getText().toString();

                if(lu.equals(Email) && lp.equals(password)){
                    FancyToast.makeText(LoginActivity.this,"Login Success",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                    Intent intent=new Intent(LoginActivity.this,LoginActivity2.class);
                    startActivity(intent);
                }
                else{
                    FancyToast.makeText(LoginActivity.this,"Login Failed Try Again!!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();
                    textv.setVisibility(View.VISIBLE);
                    textv.setBackgroundColor(Color.MAGENTA);
                    counter-=1;
                    textv.setText(counter+" attempts left");
                    if(counter==0){
                        FancyToast.makeText(LoginActivity.this,"Failed Login Attempts",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        Login.setEnabled(false);
                    }


                }
            }
        });
        cancelL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}