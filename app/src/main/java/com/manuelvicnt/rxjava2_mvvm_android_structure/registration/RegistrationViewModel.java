package com.manuelvicnt.rxjava2_mvvm_android_structure.registration;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.NetworkViewModel;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.AuthenticationRequestManager;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.account.exception.AccountTechFailureException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.games.exception.GamesTechFailureException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginInternalException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.login.exception.LoginTechFailureException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationInternalException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationNicknameAlreadyExistsException;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.registration.exception.RegistrationTechFailureException;

import io.reactivex.MaybeSource;

import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_FAILED;
import static com.manuelvicnt.rxjava2_mvvm_android_structure.base.Constants.REQUEST_SUCCEEDED;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class RegistrationViewModel extends NetworkViewModel implements RegistrationContract.ViewModel {

    private RegistrationContract.View viewCallback;

    private AuthenticationRequestManager authenticationRequestManager;

    public RegistrationViewModel(AuthenticationRequestManager authenticationRequestManager) {

        this.authenticationRequestManager = authenticationRequestManager;
    }

    @Override
    public void onViewResumed() {

        @Constants.RequestState int requestState = getRequestState();
        if (requestState == REQUEST_SUCCEEDED) {
            onRegistrationCompleted();
        } else if (requestState == REQUEST_FAILED) {
            onRegistrationError(getLastError());
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {

        this.viewCallback = (RegistrationContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {

        this.viewCallback = null;
    }

    @Override
    public boolean isRequestingInformation() {

        return authenticationRequestManager.isRequestingInformation();
    }

    public void register() {

        authenticationRequestManager.register()
                .subscribe(new RegistrationObserver());
    }

    private void onRegistrationCompleted() {

        if (viewCallback != null) {

            viewCallback.hideProgressDialog();
            viewCallback.launchHomeActivity();

        }
    }

    private void onRegistrationError(Throwable e) {

        if (viewCallback != null) {

            viewCallback.hideProgressDialog();

            if (e instanceof RegistrationInternalException
                    || e instanceof RegistrationTechFailureException) {

                viewCallback.showMessage("Registration Failure");

            } else if (e instanceof RegistrationNicknameAlreadyExistsException) {

                viewCallback.showMessage("Registration Nickname already exists");

            } else if (e instanceof LoginInternalException
                    || e instanceof LoginTechFailureException) {

                viewCallback.showMessageAndGoToLogin("Login Failure");

            } else if (e instanceof AccountTechFailureException) {

                viewCallback.showMessageAndGoToLogin("Account failed");
            } else if (e instanceof GamesTechFailureException) {

                viewCallback.showMessageAndGoToLogin("Games failed");
            } else {

                viewCallback.showMessage("Registration Failure");
            }
        }
    }

    private class RegistrationObserver extends MaybeNetworkObserver<Object> {

        @Override
        public void onSuccess(Object value) {

            onRegistrationCompleted();
        }

        @Override
        public void onError(Throwable e) {

            onRegistrationError(e);
        }

        @Override
        public void onComplete() {

        }
    }
}
