package com.manuelvicnt.rxjava2_mvvm_android_structure.data;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class AuthenticationManager {

    private static AuthenticationManager instance;

    private String nickname;
    private String password;
    private boolean userLogged;

    private AuthenticationManager() {

    }

    public static AuthenticationManager getInstance() {

        synchronized (AuthenticationManager.class) {
            if (instance == null) {
                instance = new AuthenticationManager();
            }

            return instance;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isUserLogged() {

        return userLogged;
    }

    public void logUserIn() {

        userLogged = true;
    }
}
