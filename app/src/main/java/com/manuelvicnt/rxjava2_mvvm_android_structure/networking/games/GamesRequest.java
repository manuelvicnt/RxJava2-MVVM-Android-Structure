package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class GamesRequest {

    @SerializedName("nickname") private String nickname;

    public GamesRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
