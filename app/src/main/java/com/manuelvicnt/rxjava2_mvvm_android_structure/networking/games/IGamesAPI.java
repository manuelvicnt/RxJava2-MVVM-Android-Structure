package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public interface IGamesAPI {

    @GET("games")
    Observable<GamesResponse> getGamesInformation(@Header("nickname") String nickname);
}
