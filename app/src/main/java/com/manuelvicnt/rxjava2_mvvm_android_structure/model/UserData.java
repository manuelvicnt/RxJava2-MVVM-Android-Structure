package com.manuelvicnt.rxjava2_mvvm_android_structure.model;

import java.util.List;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class UserData {

    private List<String> games;
    private String accountInformation;

    public UserData() {
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public String getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }
}
