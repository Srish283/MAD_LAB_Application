package com.srishti.validatingusers;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button signup,cancel;
    EditText name,password,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup=(Button)findViewById(R.id.signup);
        cancel=(Button)findViewById(R.id.cancel);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.etPassword);
        email=(EditText)findViewById(R.id.email);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                String $email,$password;
                $email=email.getText().toString();
                $password=password.getText().toString();
                if($email.equals("")||$password.equals("")){
                    FancyToast.makeText(getApplicationContext(),"Please enter all details",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();
                }
                else if(!isValidPassword($password.trim())){
                    FancyToast.makeText(MainActivity.this,"Invalid Password",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                }
                else{
                    FancyToast.makeText(getApplicationContext(),"Sign Up Success",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("Email",$email);
                    bundle.putString("Password",$password);
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public boolean isValidPassword(final String Password){
        Pattern pattern;
        Matcher matcher;
        final String Password_Pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#$%^*+_])(?=\\S+$).{8,}$";
        pattern=Pattern.compile(Password_Pattern);
        matcher=pattern.matcher(Password);
        return(matcher.matches());
    }

}