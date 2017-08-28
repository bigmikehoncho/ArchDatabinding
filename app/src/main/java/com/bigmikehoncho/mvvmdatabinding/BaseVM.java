package com.bigmikehoncho.mvvmdatabinding;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by mike on 8/26/17.
 */

public class BaseVM extends ViewModel {

    protected LiveData<ViewState> viewState;

    public BaseVM() {
    }
}
