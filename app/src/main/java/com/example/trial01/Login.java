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
import android.widget.Switch;
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

public class Login extends AppCompatActivity {

    private EditText mUsername, mPassword;
    private Button mLogin;
    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        Log.d("API", "onResponse" + mApiService);
        component();
    }

    public void component(){

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if (username.isEmpty()){
                    mUsername.setError("Username required");
                    loading.dismiss();
                }
                else if (password.isEmpty()){
                    mPassword.setError("Password required");
                    loading.dismiss();
                }
                else {

                    requestLogin();
//                    startActivity(new Intent(Login.this, ListUser.class));
                }
            }
        });

    }

    private void requestLogin(){
        mApiService.loginRequest(mUsername.getText().toString(), mPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getJSONObject("meta").getString("message").equals("Success")){
                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                    String nama = jsonRESULTS.getJSONObject("profile_data").getString("first_name");
//                                    Log.d("nama", "onResponse" + nama);
                                    Intent intent = new Intent(mContext, ListUser.class);
//                                    intent.putExtra("result_nama", nama);
                                    startActivity(intent);
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
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }

                });
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
