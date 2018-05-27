package com.marvelcomics.brito.marvelcomics.resources;


import android.content.Context;

import com.marvelcomics.brito.infrastructure.di.ResourceProvider;

import javax.inject.Inject;

public class ResourceProviderImpl implements ResourceProvider {

    private Context context;

    @Inject
    public ResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int stringId) {
        return context.getString(stringId);
    }

    @Override
    public String getString(int stringId, Object... params) {
        return context.getString(stringId, params);
    }
}
