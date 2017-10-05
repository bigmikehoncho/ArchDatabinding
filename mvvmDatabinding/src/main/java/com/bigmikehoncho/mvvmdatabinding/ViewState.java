package com.bigmikehoncho.mvvmdatabinding;

import android.support.annotation.Nullable;

import io.reactivex.functions.Consumer;

public class ViewState<N extends Navigator> {
    private N navigator;
    private Consumer<N> nConsumer;

    public N getNavigator() {
        return navigator;
    }

    public void setNavigator(@Nullable N navigator) {
        this.navigator = navigator;

        tryRunning();
    }

    public void apply(@Nullable Consumer<N> nConsumer) {
        this.nConsumer = nConsumer;

        tryRunning();
    }

    private void tryRunning() {
        if (navigator != null && nConsumer != null) {
            try {
                this.nConsumer.accept(navigator);
                this.nConsumer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
