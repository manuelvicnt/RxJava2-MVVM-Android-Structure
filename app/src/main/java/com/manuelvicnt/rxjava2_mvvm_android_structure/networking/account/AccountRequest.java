package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class AccountRequest {

    @SerializedName("nickname") private String nickname;

    public AccountRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
