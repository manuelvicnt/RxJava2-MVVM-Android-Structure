package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public interface IAccountAPI {

    @GET("account")
    Observable<AccountResponse> getAccountInformation(@Header("nickname") String nickname);
}
