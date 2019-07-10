package com.example.trial01.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.trial01.R;
import com.example.trial01.adapter.RecyclerViewUserAdapter;
import com.example.trial01.apihelper.BaseApiService;
import com.example.trial01.apihelper.UtilsApi;
import com.example.trial01.connection.ConnectivityReceiver;
import com.example.trial01.model.SharedPrefManager;
import com.example.trial01.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private List<User> mUser;
    private RecyclerViewUserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mEmpty;
    private ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;
    private DatabaseReference mDatabaseRef;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        sharedPrefManager = new SharedPrefManager(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        mEmpty = findViewById(R.id.empty);
        mRecyclerView = findViewById(R.id.myRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mUser = new ArrayList<>();
        mAdapter = new RecyclerViewUserAdapter (this, mUser);

        mRecyclerView.setAdapter(mAdapter);
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        checkConnection();
        getUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_list_user, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout) {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(ListUserActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        if (item.getItemId()==R.id.product) {
            startActivity(new Intent(ListUserActivity.this, ListProductActivity.class));
        }
        if (item.getItemId() == R.id.navigation_menu){
            startActivity(new Intent(ListUserActivity.this, MainActivity.class));
        }
        return true;
    }

    public void getUser(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUser.clear();

                try {

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    User upload = userSnapshot.getValue(User.class);

                        Log.d("data", "user" + upload);

                        mUser.add(upload);

                        loading.dismiss();

                }

                mAdapter.notifyDataSetChanged();

                    checkData();
                }catch (Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkData(){

        if (mAdapter.getItemCount() !=0){

            mEmpty.setVisibility(View.GONE);
        }
        else {
            mEmpty.setVisibility(View.VISIBLE);
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
            loading.dismiss();
            message = "Maaf! Tidak terhubung ke internet";
            color = Color.RED;

            Snackbar snackbar = Snackbar.make(findViewById(R.id.rl), message, Snackbar.LENGTH_LONG);

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
