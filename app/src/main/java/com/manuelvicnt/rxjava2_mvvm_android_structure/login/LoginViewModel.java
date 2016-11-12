package com.manuelvicnt.rxjava2_mvvm_android_structure.login;

import android.support.annotation.NonNull;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.NetworkViewModel;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.AuthenticationRequestManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.exception.AccountTechFailureException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.exception.GamesTechFailureException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginInternalException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginTechFailureException;

import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_FAILED;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_SUCCEEDED;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.RequestState;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class LoginViewModel extends NetworkViewModel implements LoginContract.ViewModel {

    private LoginContract.View viewCallback;

    private AuthenticationRequestManager authenticationRequestManager;

    public LoginViewModel(AuthenticationRequestManager authenticationRequestManager) {

        this.authenticationRequestManager = authenticationRequestManager;
    }

    @Override
    public void onViewResumed() {

        @RequestState int requestState = getRequestState();
        if (requestState == REQUEST_SUCCEEDED) {
            onLoginCompleted();
        } else if (requestState == REQUEST_FAILED) {
            onLoginError(getLastError());
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {

        this.viewCallback = (LoginContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {

        this.viewCallback = null;
    }

    @Override
    public boolean isRequestingInformation() {

        return authenticationRequestManager.isRequestingInformation();
    }

    @Override
    public void login() {

        authenticationRequestManager.login()
                .subscribe(new LoginObserver());
    }

    private void onLoginCompleted() {

        if (viewCallback != null) {

            viewCallback.hideProgressDialog();
            viewCallback.launchHomeActivity();
        }
    }

    private void onLoginError(Throwable e) {

        if (viewCallback != null) {

            viewCallback.hideProgressDialog();

            if (e instanceof LoginInternalException
                    || e instanceof LoginTechFailureException) {

                viewCallback.showMessage("Login Failure");

            } else if (e instanceof AccountTechFailureException) {

                viewCallback.showMessage("Account failed");
                viewCallback.launchHomeActivity();
            } else if (e instanceof GamesTechFailureException) {

                viewCallback.showMessage("Games failed");
                viewCallback.launchHomeActivity();
            }
        }
    }

    private class LoginObserver extends MaybeNetworkObserver<Object> {

        @Override
        public void onSuccess(Object value) {

            onLoginCompleted();
        }

        @Override
        public void onError(Throwable e) {

            onLoginError(e);
        }

        @Override
        public void onComplete() {

        }
    }
}
