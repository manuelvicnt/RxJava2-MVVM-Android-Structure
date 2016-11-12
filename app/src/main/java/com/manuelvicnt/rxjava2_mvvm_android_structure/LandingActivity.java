package com.manuelvicnt.rxjava2_mvvm_android_structure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.manuelvicnt.rxjava2_mvvm_android_structure.data.PrivateSharedPreferencesManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.login.LoginFragment;
import com.manuelvicnt.rxjava2_mvvm_android_structure.registration.RegistrationFragment;

import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            return;
        }


        String userNickname = PrivateSharedPreferencesManager.getInstance(getApplicationContext()).getUserNickname();
        Fragment initialFragment;

        if (userNickname == null || userNickname.isEmpty()) {

            setTitle("Registration");
            initialFragment = new RegistrationFragment();
        } else {

            setTitle("Login");
            initialFragment = new LoginFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, initialFragment).commit();
    }
}

