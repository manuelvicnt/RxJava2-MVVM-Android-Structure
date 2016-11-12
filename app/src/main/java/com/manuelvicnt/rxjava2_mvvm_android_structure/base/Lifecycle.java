package com.manuelvicnt.rxjava2_mvvm_android_structure.base;

import android.support.annotation.NonNull;

/**
 * Created by manuelvicnt on 11/11/2016.
 */
public interface Lifecycle {

    interface View {

    }

    interface ViewModel {

        void onViewResumed();
        void onViewAttached(@NonNull Lifecycle.View viewCallback);
        void onViewDetached();
    }
}
