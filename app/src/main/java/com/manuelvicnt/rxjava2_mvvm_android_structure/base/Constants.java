package com.manuelvicnt.rxjava2_mvvm_android_structure.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by manuelvicnt on 11/11/2016.
 */

public class Constants {

    @IntDef({
            REQUEST_NONE,
            REQUEST_RUNNING,
            REQUEST_SUCCEEDED,
            REQUEST_FAILED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestState{}

    public static final int REQUEST_NONE = 0;
    public static final int REQUEST_RUNNING = 1;
    public static final int REQUEST_SUCCEEDED = 2;
    public static final int REQUEST_FAILED = 3;
}
