package com.marvelcomics.brito.marvelcomics.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.marvelcomics.brito.marvelcomics.ui.ResourceModelHandler;
import com.marvelcomics.brito.presentation.ResourceModel;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected ResourceModelHandler resourceModelHandler;

    protected <T extends ResourceModel> void onModelChanged(T listResourceModel,
                                                            ResourceModelHandler.ResourceModelListener<T> listener) {
        resourceModelHandler.onModelChanged(listResourceModel, listener);
    }
}
