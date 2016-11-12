package com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class LoginRequest {

    @SerializedName("nickname") private String nickname;
    @SerializedName("password") private String password;

    public LoginRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
