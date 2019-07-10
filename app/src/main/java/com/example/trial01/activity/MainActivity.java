package com.example.trial01.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.trial01.R;
import com.example.trial01.fragment.DashboardFragment;
import com.example.trial01.fragment.HomeFragment;
import com.example.trial01.fragment.InboxFragment;
import com.example.trial01.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        componentView();
        loadFragment(new HomeFragment());

    }

    private void componentView() {

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home :
                fragment = new HomeFragment();
                break;
            case  R.id.navigation_dashboard :
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_inbox :
                fragment = new InboxFragment();
                break;
            case R.id.navigation_profile :
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
