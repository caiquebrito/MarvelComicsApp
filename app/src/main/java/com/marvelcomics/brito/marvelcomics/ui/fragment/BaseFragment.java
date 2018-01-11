package com.marvelcomics.brito.marvelcomics.ui.fragment;

import android.support.v4.app.Fragment;

import com.marvelcomics.brito.marvelcomics.ui.ResourceModelHandler;
import com.marvelcomics.brito.presentation.ResourceModel;

import javax.inject.Inject;

public class BaseFragment extends Fragment {

    @Inject
    protected ResourceModelHandler resourceModelHandler;

    protected <T extends ResourceModel> void onModelChanged(T listResourceModel,
                                  ResourceModelHandler.ResourceModelListener<T> listener) {
        resourceModelHandler.onModelChanged(listResourceModel, listener);
    }
}
