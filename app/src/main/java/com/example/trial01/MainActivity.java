package com.example.trial01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Html.fromHtml("asdfsg");
    }
}
