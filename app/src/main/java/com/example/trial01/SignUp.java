package com.example.trial01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    private EditText mName, mUsername, mPassword;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onClickView(View view){

        switch (view.getId()){
            case R.id.signUp :

                break;
            case R.id.txt_login :
                startActivity(new Intent(SignUp.this, Login.class));
                break;
        }
    }

    public void widget(){

        mName = findViewById(R.id.name);
        mUsername = findViewById(R.id.username);
        mButton = findViewById(R.id.signUp);
    }

}
