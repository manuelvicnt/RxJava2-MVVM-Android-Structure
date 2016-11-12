package com.manuelvicnt.rxjava2_mvvm_android_structure.networking;

import android.content.Context;

import com.manuelvicnt.rxjava2_mvvm_android_structure.data.AuthenticationManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.data.PrivateSharedPreferencesManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.LoginAPIService;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.LoginRequest;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.LoginResponse;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginInternalException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.mock.RetrofitFactory;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.RegistrationAPIService;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.RegistrationRequest;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationInternalException;

import io.reactivex.MaybeSource;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class AuthenticationRequestManager {

    private static AuthenticationRequestManager instance;

    private AuthenticationManager authenticationManager;
    private PrivateSharedPreferencesManager privateSharedPreferencesManager;

    private RegistrationAPIService registrationAPIService;
    private LoginAPIService loginAPIService;
    private UserDataRequestManager userDataRequestManager;

    private AuthenticationRequestManager(Context context) {

        this.authenticationManager = AuthenticationManager.getInstance();

        privateSharedPreferencesManager = PrivateSharedPreferencesManager.getInstance(context);
        Retrofit retrofit = RetrofitFactory.getAdapter();

        this.registrationAPIService = new RegistrationAPIService(retrofit, privateSharedPreferencesManager);
        this.loginAPIService = new LoginAPIService(retrofit, authenticationManager);

        this.userDataRequestManager = UserDataRequestManager.getInstance(context);
    }

    public static AuthenticationRequestManager getInstance(Context context) {

        synchronized (AuthenticationRequestManager.class) {
            if (instance == null) {
                instance = new AuthenticationRequestManager(context);
            }

            return instance;
        }
    }

    public boolean isRequestingInformation() {

        return registrationAPIService.isRequestingRegistration() ||
                loginAPIService.isRequestingLogin() ||
                userDataRequestManager.isRequestingInformation();
    }

    public MaybeSource<Object> register() {

        return registrationAPIService.register(createBodyForRegistration())
                .andThen(makeLoginRequest());
    }

    public MaybeSource<Object> login() {

        return loginAPIService.login(createLoginRequest())
                .flatMap(this::makeGetUserDataRequest);
    }

    private MaybeSource<Object> makeLoginRequest() {

        return login();
    }

    private MaybeSource<Object> makeGetUserDataRequest(LoginResponse loginResponse) {

        return userDataRequestManager.getUserData().singleElement();
    }

    private LoginRequest createLoginRequest() {

        String nickname = authenticationManager.getNickname();
        String password = authenticationManager.getPassword();

        if (nickname == null || nickname.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new LoginInternalException();
        }

        return new LoginRequest(nickname, password);
    }

    private RegistrationRequest createBodyForRegistration() {

        String nickname = authenticationManager.getNickname();
        String password = authenticationManager.getPassword();

        if (nickname == null || nickname.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new RegistrationInternalException();
        }

        return new RegistrationRequest(nickname, password);
    }
}
