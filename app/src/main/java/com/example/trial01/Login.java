package com.example.trial01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Login extends AppCompatActivity {

    private EditText mUsername, mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        widget();
    }

    public void widget(){

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);

    }

    public void onClickView(View view){

        switch (view.getId()){
            case R.id.login :

                break;
            case R.id.txt_signUp :
                startActivity(new Intent(Login.this, SignUp.class));
                break;
        }
    }
}
