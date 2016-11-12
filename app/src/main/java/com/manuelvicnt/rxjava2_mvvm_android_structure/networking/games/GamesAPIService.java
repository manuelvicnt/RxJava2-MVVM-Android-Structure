package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games;

import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.exception.GamesTechFailureException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class GamesAPIService {

    private IGamesAPI gamesAPI;
    private boolean isRequestingGames;

    public GamesAPIService(Retrofit retrofit) {

        this.gamesAPI = retrofit.create(IGamesAPI.class);
    }

    public boolean isRequestingGames() {
        return isRequestingGames;
    }

    public Flowable<GamesResponse> getGames(GamesRequest request) {

        return gamesAPI.getGamesInformation(request.getNickname())
                .doOnSubscribe(disposable -> isRequestingGames = true)
                .doOnTerminate(() -> isRequestingGames = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::handleAccountError)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private void handleAccountError(Throwable throwable) {

        throw new GamesTechFailureException();
    }
}
