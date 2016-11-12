package com.manuelvicnt.rxjava2_mvvm_android_structure.data;

import com.manuelvicnt.rxjava2_mvvm_android_structure.model.UserData;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class DataManager {

    private static DataManager instance;
    private UserData userData;

    private DataManager() {

        userData = new UserData();
    }

    public static DataManager getInstance() {

        synchronized (DataManager.class) {
            if (instance == null) {
                instance = new DataManager();
            }

            return instance;
        }
    }

    public UserData getUserData() {

        return userData;
    }
}
