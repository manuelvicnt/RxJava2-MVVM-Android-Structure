package com.manuelvicnt.rxjava2_mvvm_android_structure.registration;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;

/**
 * Created by manuelvicnt on 11/11/2016.
 */

public interface RegistrationContract {

    interface View extends Lifecycle.View {

        void hideProgressDialog();

        void launchHomeActivity();

        void showMessageAndGoToLogin(String message);

        void showMessage(String message);
    }

    interface ViewModel extends Lifecycle.ViewModel {

        void register();
    }
}
