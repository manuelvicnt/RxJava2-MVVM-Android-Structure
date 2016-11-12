package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public interface ILoginAPI {

    @GET("login")
    Observable<LoginResponse> login(@Header("nickname") String nickname, @Header("password") String password);
}
