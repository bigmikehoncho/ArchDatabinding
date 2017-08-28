/*
 * Copyright 2016 Manas Chaudhari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigmikehoncho.mvvmdatabinding.adapters;

import android.arch.lifecycle.ViewModel;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter<VM extends ViewModel> extends PagerAdapter implements Connectable {

    @NonNull
    private final ObservableList<VM> source;

    @NonNull
    private final ViewProvider viewProvider;

    @NonNull
    private final ViewModelBinder binder;

    private final
    @NonNull
    ObservableList.OnListChangedCallback<ObservableList<VM>> onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<VM>>() {
        @Override
        public void onChanged(ObservableList<VM> viewModels) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<VM> viewModels, int start, int count) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList<VM> viewModels, int start, int count) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList<VM> viewModels, int from, int to, int count) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList<VM> viewModels, int start, int count) {
            notifyDataSetChanged();
        }
    };

    public ViewPagerAdapter(@NonNull ObservableList<VM> viewModels, @NonNull ViewProvider viewProvider, @NonNull ViewModelBinder binder) {
        source = viewModels;
        this.viewProvider = viewProvider;
        this.binder = binder;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        VM vm = source.get(position);
        int layoutId = viewProvider.getView(vm);
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                layoutId,
                container,
                false);
        binder.bind(binding, vm);
        binding.getRoot().setTag(vm);
        container.addView(binding.getRoot());
        return binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewDataBinding binding = (ViewDataBinding) object;
        binder.bind(binding, null);
        binding.executePendingBindings();
        container.removeView(binding.getRoot());
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((ViewDataBinding) object).getRoot() == view;
    }

    @Override
    public void connect() {
        source.addOnListChangedCallback(onListChangedCallback);
    }

    @NonNull
    @Override
    public void removeCallback() {
        source.removeOnListChangedCallback(onListChangedCallback);
    }
}
