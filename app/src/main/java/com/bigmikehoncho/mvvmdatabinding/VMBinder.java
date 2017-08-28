package com.bigmikehoncho.mvvmdatabinding;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

/**
 * Created by mike on 3/4/17.
 */

public interface VMBinder {
    
    @NonNull
    ViewModel createViewModel();
    
    @LayoutRes
    int getLayoutId();
}
