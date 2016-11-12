package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration;


import com.manuelvicnt.rxjava2_mvvm_android_structure.data.PrivateSharedPreferencesManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationNicknameAlreadyExistsException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationTechFailureException;

import java.net.HttpRetryException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class RegistrationAPIService {

    private IRegistrationAPI registrationAPI;
    private PrivateSharedPreferencesManager privateSharedPreferencesManager;
    private boolean isRequestingRegistration;

    public RegistrationAPIService(Retrofit retrofit, PrivateSharedPreferencesManager privateSharedPreferencesManager) {

        this.registrationAPI = retrofit.create(IRegistrationAPI.class);
        this.privateSharedPreferencesManager = privateSharedPreferencesManager;
    }

    public boolean isRequestingRegistration() {
        return isRequestingRegistration;
    }

    public Completable register(RegistrationRequest request) {

        return registrationAPI.register(request)
                .doOnSubscribe(disposable -> isRequestingRegistration = true)
                .doOnTerminate(() -> isRequestingRegistration = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(this::handleRegistrationError)
                .doOnNext(registrationResponse -> processRegistrationResponse(request, registrationResponse))
                .ignoreElements();
    }

    private Observable<RegistrationResponse> handleRegistrationError(Throwable throwable) {

        if (throwable instanceof HttpRetryException) {

            int status = ((HttpRetryException) throwable).responseCode();

            if (status == 401) {
                throw new RegistrationNicknameAlreadyExistsException();
            } else {
                throw new RegistrationTechFailureException();
            }

        } else {
            throw new RegistrationTechFailureException();
        }
    }

    private void processRegistrationResponse(RegistrationRequest registrationRequest, RegistrationResponse registrationResponse) {

        privateSharedPreferencesManager.storeUserNickname(registrationRequest.getNickname());
    }
}
