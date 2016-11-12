package com.manuelvicnt.rxjava2_mvvm_android_structure.home;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;

/**
 * Created by manuelvicnt on 11/11/2016.
 */

public interface HomeContract {

    interface View extends Lifecycle.View {

        void showSuccessfulMessage(String message);

        void hideLoading();
    }

    interface ViewModel extends Lifecycle.ViewModel {

        void getUserData();
    }
}
