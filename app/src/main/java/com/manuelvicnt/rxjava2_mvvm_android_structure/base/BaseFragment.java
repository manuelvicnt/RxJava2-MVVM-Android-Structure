package com.manuelvicnt.rxjava2_mvvm_android_structure.base;

import android.support.v4.app.Fragment;

/**
 * Created by manuelvicnt on 11/11/2016.
 */

public abstract class BaseFragment extends Fragment implements Lifecycle.View {

    protected abstract Lifecycle.ViewModel getViewModel();

    @Override
    public void onResume() {

        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    public void onStart() {

        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    public void onStop() {

        super.onStop();
        getViewModel().onViewDetached();
    }
}
