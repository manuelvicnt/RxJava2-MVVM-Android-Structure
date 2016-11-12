package com.manuelvicnt.rxjava2_mvvm_android_structure.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class PrivateSharedPreferencesManager {

    public static final String SHARED_PREFERENCES_KEY = "com.manuelvicnt.rxjava2_mvvm_android_structure";
    public static final String NICKNAME_KEY = "nickname";

    private static PrivateSharedPreferencesManager instance;

    private SharedPreferences privateSharedPreferences;

    private PrivateSharedPreferencesManager(Context context) {

        this.privateSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public static PrivateSharedPreferencesManager getInstance(Context context) {

        synchronized (PrivateSharedPreferencesManager.class) {
            if (instance == null) {
                instance = new PrivateSharedPreferencesManager(context);
            }
            return instance;
        }
    }

    private void storeStringInSharedPreferences(String key, String content) {

        SharedPreferences.Editor editor = privateSharedPreferences.edit();
        editor.putString(key, content);
        editor.apply();
    }

    private String getStringFromSharedPreferences(String key) {

        return privateSharedPreferences.getString(key, "");
    }

    public void storeUserNickname(String nickname) {

        storeStringInSharedPreferences(NICKNAME_KEY, nickname);
    }

    public String getUserNickname() {

        return getStringFromSharedPreferences(NICKNAME_KEY);
    }
}
