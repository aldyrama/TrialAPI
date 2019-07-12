package com.example.trial01.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trial01.R;

import java.util.Scanner;

public class TrianglePatternActivity extends AppCompatActivity {

    private TextView mPatternResults, mPatternResults1, mPatternResults2;
    private Button mButtonResults;
    private TextInputEditText mInput;
    String patternResult = "";
    int value, i, j, k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_pattern);

        mInput = findViewById(R.id.etx_pattern);
        mButtonResults = findViewById(R.id.btn_pattern);
        mPatternResults = findViewById(R.id.txt_pattern);
        mPatternResults1 = findViewById(R.id.txt_pattern2);
        mPatternResults2 = findViewById(R.id.txt_pattern3);
        pattern();

    }

    public void pattern(){
        mButtonResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Patter 1
                patternResult = "";
                if (mInput.getText().toString().isEmpty()) {
                    mInput.setError("input required");
                } else {
                    value = Integer.parseInt(mInput.getText().toString());
//                    mPatternResults1.setText("*");
                    Log.d("result", "input" + value);

                    for (i = value; i > 0; i--) {
                        for (j = 0; j < i; j++) {
                            System.out.print("*");
                            patternResult += "*";

                        }
                        System.out.println();
                        patternResult += "\n";
                    }

                    mPatternResults1.setText(patternResult);
                    Log.d("result", "input" + patternResult);

                    //Pattern 2
                    patternResult = "";
                    for (i = 0; i <= value; i++) {
                        for (j = 0; j < i; j++) {
                            System.out.print("*");
                            patternResult += "*";
                        }

                        System.out.println();
                        patternResult += "\n";

                    }
                    mPatternResults.setText(patternResult);

                    //Pattern 3
                    patternResult = "";
                    for (i=1; i<=value; i++){
                        for (j=i; j <= value - 1; j++){
                            System.out.println(" ");
                            patternResult += " ";
                    }
                    for (k = 1; k <= (2* i) - 1; k++) {
                        System.out.print("*");
                        patternResult += "*";
                    }
                        System.out.println();
                        patternResult += "\n";
                    }
                    mPatternResults2.setText(patternResult);

                }

            }

        });

    }
}
