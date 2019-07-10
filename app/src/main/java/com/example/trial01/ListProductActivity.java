package com.example.trial01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trial01.adapter.RecyclerViewProductAdapter;
import com.example.trial01.apihelper.BaseApiService;
import com.example.trial01.apihelper.UtilsApi;
import com.example.trial01.connection.ConnectivityListener;
import com.example.trial01.connection.ConnectivityReceiver;
import com.example.trial01.model.Data;
import com.example.trial01.model.Product;
import com.example.trial01.model.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private SharedPrefManager sharedPrefManager;
    private Menu menu;
    private List<Product> mProduct;
    private RecyclerViewProductAdapter mAdapter;
    private TextView mEmpty;
    private RecyclerView mRecyclerView;
    private ProgressDialog loading;
    private int page;
    private SwipeRefreshLayout mSwipLayout;
    private RelativeLayout mRelatifLayout;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        sharedPrefManager = new SharedPrefManager(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        mEmpty = findViewById(R.id.empty);
        mRecyclerView = findViewById(R.id.recycler_product);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProduct = new ArrayList<>();
        mAdapter = new RecyclerViewProductAdapter(this, mProduct);
        mRecyclerView.setAdapter(mAdapter);
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        mApiService = UtilsApi.getAPIService();
        checkConnection();
        componentView();
        getProduct();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_list_product, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout) {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(ListProductActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        if (item.getItemId()==R.id.user) {
            startActivity(new Intent(ListProductActivity.this, ListUserActivity.class));
        }
        if (item.getItemId() == R.id.page1){
            loading.show();
            checkConnection();
            getProduct();
            Log.d("API", "Data" + page);

        }
        if (item.getItemId() == R.id.page2){
            loading.show();
            checkConnection();
            getProduct1();
            Log.d("API", "Data" + page);

        }
        if (item.getItemId() == R.id.page3){
            loading.show();
            checkConnection();
            getProduct2();
        }


        return true;
    }

    private void componentView() {
        mRelatifLayout = findViewById(R.id.rl);
//        mSwipLayout = findViewById(R.id.container);

//        mSwipLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
//
//        mSwipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                new Handler().postDelayed(new Runnable() {
//                    @SuppressLint("NewApi")
//                    @Override public void run() {
//                        checkConnection();
//                        mSwipLayout.setRefreshing(false);
//
//                    }
//                }, 2500);
//            }
//        });
    }

    public void getProduct() {

        mApiService.products(1).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("API", "Data" + response);
                mProduct.clear();
                mProduct.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
                loading.dismiss();
                checkData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void getProduct1() {

        mApiService.products(2).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("API", "Data" + response);
                mProduct.clear();
                mProduct.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
                loading.dismiss();
                checkData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void getProduct2() {

        mApiService.products(3).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("API", "Data" + response);
                mProduct.clear();
                mProduct.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
                loading.dismiss();
                checkData();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

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
