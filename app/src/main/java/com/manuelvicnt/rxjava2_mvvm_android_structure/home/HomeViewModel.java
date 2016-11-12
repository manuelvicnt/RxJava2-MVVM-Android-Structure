package com.manuelvicnt.rxjava2_mvvm_android_structure.home;

import android.support.annotation.NonNull;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.UserDataRequestManager;

import io.reactivex.disposables.Disposable;
import io.reactivex.processors.AsyncProcessor;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class HomeViewModel implements HomeContract.ViewModel {

    private HomeContract.View viewCallback;

    private AsyncProcessor<Object> userDataProcessor;
    private Disposable userDataDisposable;

    private UserDataRequestManager userDataRequestManager;

    public HomeViewModel(UserDataRequestManager userDataRequestManager) {

        this.userDataRequestManager = userDataRequestManager;
    }

    @Override
    public void onViewResumed() {

        if (isNetworkRequestMade()) {

            userDataProcessor.subscribe(new HomeSubscriber());
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {

        this.viewCallback = (HomeContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {

        this.viewCallback = null;

        if (isNetworkRequestMade()) {
            userDataDisposable.dispose();
        }
    }

    public void getUserData() {

        userDataProcessor = AsyncProcessor.create();
        userDataDisposable = userDataProcessor.subscribeWith(new HomeSubscriber());

        userDataRequestManager.getUserData().subscribe(userDataProcessor);
    }

    private boolean isNetworkRequestMade() {

        return userDataDisposable != null;
    }

    private class HomeSubscriber extends DisposableSubscriber<Object> {

        @Override
        public void onNext(Object o) {

        }

        @Override
        public void onError(Throwable t) {

            viewCallback.showSuccessfulMessage("Refreshed");
        }

        @Override
        public void onComplete() {

            viewCallback.showSuccessfulMessage("Refreshed");
            viewCallback.hideLoading();
        }
    }
}
