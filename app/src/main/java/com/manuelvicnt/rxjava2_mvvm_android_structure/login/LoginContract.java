package com.manuelvicnt.rxjava2_mvvm_android_structure.login;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;

/**
 * Created by manuelvicnt on 11/11/2016.
 */

public interface LoginContract {

    interface View extends Lifecycle.View {

        void hideProgressDialog();

        void launchHomeActivity();

        void showMessage(String message);
    }

    interface ViewModel extends Lifecycle.ViewModel {

        void login();
    }
}
