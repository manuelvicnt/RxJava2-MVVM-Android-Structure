package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public interface IRegistrationAPI {

    @POST("registration")
    Observable<RegistrationResponse> register(@Body RegistrationRequest request);
}
