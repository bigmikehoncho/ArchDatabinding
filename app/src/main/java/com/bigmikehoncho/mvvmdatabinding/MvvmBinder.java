package com.bigmikehoncho.mvvmdatabinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.bigmikehoncho.mvvmdatabinding.adapters.ViewModelBinder;
import com.bigmikehoncho.mvvmdatabinding.utils.BindingUtils;
import com.bigmikehoncho.mvvmdatabinding.utils.Preconditions;

/**
 * Created by mike on 3/3/17.
 */

public class MvvmBinder<Binding extends ViewDataBinding> {
    private Binding binding;

    public MvvmBinder() {
    }

    /**
     * Call after super.onCreate of activity
     *
     * @param activity
     */
    public Binding onCreate(Activity activity) {
        VMBinder binder = (VMBinder) activity;
        binding = DataBindingUtil.setContentView(activity, binder.getLayoutId());
        getDefaultBinder().bind(binding, binder.createViewModel());
        return binding;
    }

    public Binding onCreate(VMBinder binder, LayoutInflater inflater) {
        binding = DataBindingUtil.inflate(inflater, binder.getLayoutId(), null, false);
        getDefaultBinder().bind(binding, binder.createViewModel());
        return binding;
    }

    /**
     * call before super.onDestroy or super.onDestroyView
     */
    public void onDestroy() {
        getDefaultBinder().bind(binding, null);
        binding.executePendingBindings();
    }

    @NonNull
    private ViewModelBinder getDefaultBinder() {
        ViewModelBinder defaultBinder = BindingUtils.getDefaultBinder();
        Preconditions.checkNotNull(defaultBinder, "Default Binder");
        return defaultBinder;
    }
}
