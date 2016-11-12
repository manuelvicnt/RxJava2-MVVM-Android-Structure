package com.manuelvicnt.rxjava2_mvvm_android_structure.networking;

import android.content.Context;

import com.manuelvicnt.rxjava2_mvvm_android_structure.data.DataManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.model.UserData;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.AccountAPIService;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.AccountRequest;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.AccountResponse;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.GamesAPIService;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.GamesRequest;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.GamesResponse;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.mock.RetrofitFactory;

import java.util.Collections;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class UserDataRequestManager {

    private static UserDataRequestManager instance;

    private DataManager dataManager;

    private AccountAPIService accountAPIService;
    private GamesAPIService gamesAPIService;

    private UserDataRequestManager(Context context) {

        dataManager = DataManager.getInstance();

        Retrofit retrofit = RetrofitFactory.getAdapter();
        accountAPIService = new AccountAPIService(retrofit);
        gamesAPIService = new GamesAPIService(retrofit);
    }

    public static UserDataRequestManager getInstance(Context context) {

        synchronized (UserDataRequestManager.class) {
            if (instance == null) {
                instance = new UserDataRequestManager(context);
            }

            return instance;
        }
    }

    public boolean isRequestingInformation() {

        return accountAPIService.isRequestingAccount() ||
                gamesAPIService.isRequestingGames();
    }

    public Flowable<Object> getUserData() {

        return Flowable.zip(
                    getAccount(),
                    getGames(),
                    this::processUserDataResult);
    }

    private Object processUserDataResult(AccountResponse accountResponse, GamesResponse gamesResponse) {

        UserData userData = dataManager.getUserData();
        userData.setAccountInformation("get information from the account response");
        userData.setGames(Collections.EMPTY_LIST);
        return new Object();
    }

    private Flowable<AccountResponse> getAccount() {

        return accountAPIService.getAccount(createAccountRequest());
    }
    
    private Flowable<GamesResponse> getGames() {

        return gamesAPIService.getGames(createGamesRequest());
    }

    private GamesRequest createGamesRequest() {

        return new GamesRequest("nickname");
    }

    private AccountRequest createAccountRequest() {

        return new AccountRequest("nickname");
    }
}
