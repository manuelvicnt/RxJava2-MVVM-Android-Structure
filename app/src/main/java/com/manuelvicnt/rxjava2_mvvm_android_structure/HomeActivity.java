package com.manuelvicnt.rxjava2_mvvm_android_structure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.manuelvicnt.rxjava2_mvvm_android_structure.home.HomeFragment;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            return;
        }

        setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment).commit();
    }
}

