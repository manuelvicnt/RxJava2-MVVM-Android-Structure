package com.manuelvicnt.rxjava2_mvvm_android_structure.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manuelvicnt.rxjava2_mvvm_android_structure.HomeActivity;
import com.manuelvicnt.rxjava2_mvvm_android_structure.R;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.BaseFragment;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;
import com.manuelvicnt.rxjava2_mvvm_android_structure.data.AuthenticationManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.AuthenticationRequestManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.ViewModel loginViewModel;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationRequestManager authenticationRequestManager =
                AuthenticationRequestManager.getInstance(getActivity().getApplicationContext());

        loginViewModel = new LoginViewModel(authenticationRequestManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {

        return loginViewModel;
    }

    @OnClick(R.id.login)
    public void loginButtonTap(View view) {

        AuthenticationManager.getInstance().setNickname("nickName");
        AuthenticationManager.getInstance().setPassword("password");
        loginViewModel.login();

        progressDialog = ProgressDialog.show(getActivity(), "Login", "...", true);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.dismiss();
        }
    }

    @Override
    public void launchHomeActivity() {

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
