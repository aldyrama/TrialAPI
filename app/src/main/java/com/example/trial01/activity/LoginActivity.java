package com.example.trial01.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trial01.R;
import com.example.trial01.apihelper.BaseApiService;
import com.example.trial01.apihelper.UtilsApi;
import com.example.trial01.connection.ConnectivityReceiver;
import com.example.trial01.model.SharedPrefManager;
import com.example.trial01.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private TextInputEditText mUsername, mPassword;
    private AppCompatButton mLogin;
    private DatabaseReference ref;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, ListUserActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        Log.d("API", "onResponse" + mApiService);
        component();
    }

    public void component(){

        mUsername = findViewById(R.id.etx_username);
        mPassword = findViewById(R.id.etx_password);
        mLogin = findViewById(R.id.btn_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
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

                }
            }
        });

    }

    public void addUser(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        User user = new User(username, password);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        String id = ref.push().getKey();
        ref.child(id).setValue(user);
        ref.child(id).child("id").setValue(id);

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
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
//                                    Log.d("nama", "onResponse" + nama);
                                    addUser();
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {

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
            case R.id.txt_signUp :
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected(this);
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message = null;
        int color = 0;
        if (!isConnected) {
//            loading.dismiss();
            message = "Maaf! Tidak terhubung ke internet";
            color = Color.RED;

            Snackbar snackbar = Snackbar.make(findViewById(R.id.scrool), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();

        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack(isConnected);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
