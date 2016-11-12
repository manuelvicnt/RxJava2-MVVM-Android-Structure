package com.manuelvicnt.rxjava2_mvvm_android_structure.registration;

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
import com.manuelvicnt.rxjava2_mvvm_android_structure.login.LoginFragment;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.AuthenticationRequestManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class RegistrationFragment extends BaseFragment implements RegistrationContract.View {

    private RegistrationContract.ViewModel registrationViewModel;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationRequestManager authenticationRequestManager =
                AuthenticationRequestManager.getInstance(getActivity().getApplicationContext());

        registrationViewModel = new RegistrationViewModel(authenticationRequestManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {

        return registrationViewModel;
    }

    @OnClick(R.id.register)
    public void registerButtonTap(View view) {

        AuthenticationManager.getInstance().setNickname("nickname");
        AuthenticationManager.getInstance().setPassword("password");
        registrationViewModel.register();

        progressDialog = ProgressDialog.show(getActivity(), "Registering", "...", true);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageAndGoToLogin(String message) {

        showMessage(message);

        getActivity().setTitle("Login");
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment()).commit();
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
