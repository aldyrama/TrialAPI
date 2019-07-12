package com.example.trial01.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.trial01.R;
import com.example.trial01.utils.PaginationScrollListener;
import com.example.trial01.adapter.RecyclerViewProductAdapter;
import com.example.trial01.apihelper.BaseApiService;
import com.example.trial01.apihelper.UtilsApi;
import com.example.trial01.connection.ConnectivityReceiver;
import com.example.trial01.model.Data;
import com.example.trial01.model.Product;
import com.example.trial01.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    String TAG = MainActivity.class.getSimpleName();
    private SharedPrefManager sharedPrefManager;
    private List<Product> mProduct;
    private RecyclerViewProductAdapter mAdapter;
    private TextView mEmpty;
    private RecyclerView mRecyclerView;
    private ProgressDialog loading;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipLayout;
    private RelativeLayout mRelatifLayout;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();
        mEmpty = findViewById(R.id.empty);
        mEmpty.setVisibility(View.GONE);
        mApiService = UtilsApi.getAPIService();
        checkConnection();
        componentView();

        try {

            mContext = this;
            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mProduct = new ArrayList<>();
            mAdapter = new RecyclerViewProductAdapter(mContext);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    if (!isLastPage) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadDataProduct(page);
                            }
                        }, 200);
                    }
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            loadDataProduct(page);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
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
        if (item.getItemId() == R.id.navigation_menu){
            startActivity(new Intent(ListProductActivity.this, MainActivity.class));
        }
        if (item.getItemId() == R.id.pattern){
            startActivity(new Intent(ListProductActivity.this, TrianglePatternActivity.class));
        }

        return true;
    }

    private void componentView() {
        mRelatifLayout = findViewById(R.id.rl);
        mProgressBar = findViewById(R.id.progress_bar);
//        swipeRefreshLayout = findViewById(R.id.main_swiperefresh);

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

    private void loadDataProduct(int page) {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<Data> call = mApiService.products(page);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data serverResponse = response.body();
                resultAction(serverResponse);
                Log.d("respons", "result" + response);

                checkData();
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("failure", t.toString());

            }
        });
    }

    private void resultAction(Data data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        isLoading = false;
        if (data != null) {
            mAdapter.addItems(data.getData());
            if (data.getPage() == data.getTotalPages()) {
                isLastPage = true;
            } else {
                page = data.getPage() + 1;
            }
        }
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
//            mProgressBar.setVisibility(View.GONE);
            message = "Maaf! Tidak terhubung ke internet";
            color = Color.RED;

            Snackbar snackbar = Snackbar.make(findViewById(R.id.constraint), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
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
