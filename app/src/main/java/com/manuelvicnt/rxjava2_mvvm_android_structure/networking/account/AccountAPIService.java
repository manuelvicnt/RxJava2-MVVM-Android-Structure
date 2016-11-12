package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account;

import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.exception.AccountTechFailureException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class AccountAPIService {

    private IAccountAPI accountAPI;
    private boolean isRequestingAccount;

    public AccountAPIService(Retrofit retrofit) {

        this.accountAPI = retrofit.create(IAccountAPI.class);
    }

    public boolean isRequestingAccount() {
        return isRequestingAccount;
    }

    public Flowable<AccountResponse> getAccount(AccountRequest request) {

        return accountAPI.getAccountInformation(request.getNickname())
                .doOnSubscribe(disposable -> isRequestingAccount = true)
                .doOnTerminate(() -> isRequestingAccount = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::handleAccountError)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private void handleAccountError(Throwable throwable) {

        throw new AccountTechFailureException();
    }
}
