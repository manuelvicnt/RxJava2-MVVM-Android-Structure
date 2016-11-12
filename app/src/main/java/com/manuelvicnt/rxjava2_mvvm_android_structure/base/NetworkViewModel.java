package com.manuelvicnt.rxjava2_mvvm_android_structure.base;

import io.reactivex.observers.DisposableMaybeObserver;

import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_FAILED;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_NONE;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_RUNNING;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_SUCCEEDED;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.RequestState;

/**
 * Created by manuelvicnt on 11/11/2016.
 */
public abstract class NetworkViewModel {

    protected @RequestState int requestState;
    protected Throwable lastError;

    public abstract boolean isRequestingInformation();

    public NetworkViewModel() {

        this.requestState = REQUEST_NONE;
    }

    public @RequestState int getRequestState() {

        if (isRequestingInformation()) {
            return REQUEST_RUNNING;
        }

        return requestState;
    }

    public Throwable getLastError() {

        return lastError;
    }

    protected class MaybeNetworkObserver<T> extends DisposableMaybeObserver<T> {

        @Override
        public void onSuccess(T value) {

            requestState = REQUEST_SUCCEEDED;
        }

        @Override
        public void onError(Throwable e) {

            lastError = e;
            requestState = REQUEST_FAILED;
        }

        @Override
        public void onComplete() {

        }
    }
}
