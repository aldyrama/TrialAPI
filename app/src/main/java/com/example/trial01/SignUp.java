package com.example.trial01;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trial01.apihelper.BaseApiService;
import com.example.trial01.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private EditText mName, mUsername, mPassword;
    private Button mRegister;
    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        component();
    }

    public void component(){

        mName = findViewById(R.id.name);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mRegister = findViewById(R.id.signUp);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                String name = mName.getText().toString();
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if (name.isEmpty()){
                    mName.setError("Name required");
                    loading.dismiss();
                }
                else if (username.isEmpty()){
                    mUsername.setError("Username required");
                }
                else if (password.isEmpty()){
                    mPassword.setError("Password required");
                    loading.dismiss();
                }
                else {
                    requestRegister();
                }
            }
        });
    }

    private void requestRegister(){
        mApiService.registerRequest(mName.getText().toString(),
                mUsername.getText().toString(),
                mPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
//                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("Success")){
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, Login.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
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

}
