package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login;

import com.manuelvicnt.rxjava2_mvvm_android_structure.data.AuthenticationManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginTechFailureException;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class LoginAPIService {

    private ILoginAPI loginAPI;
    private boolean isRequestingLogin;
    private AuthenticationManager authenticationManager;

    public LoginAPIService(Retrofit retrofit, AuthenticationManager authenticationManager) {

        this.loginAPI = retrofit.create(ILoginAPI.class);
        this.authenticationManager = authenticationManager;
    }

    public boolean isRequestingLogin() {
        return isRequestingLogin;
    }

    public Maybe<LoginResponse> login(LoginRequest request) {

        return loginAPI.login(request.getNickname(), request.getPassword())
                .doOnSubscribe(disposable -> isRequestingLogin = true)
                .doOnTerminate(() -> isRequestingLogin = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(this::handleLoginError)
                .doOnNext(this::processLoginResponse)
                .singleElement();
    }

    private Observable<LoginResponse> handleLoginError(Throwable throwable) {

        throw new LoginTechFailureException();
    }

    private void processLoginResponse(LoginResponse loginResponse) {

        authenticationManager.logUserIn();
    }
}
