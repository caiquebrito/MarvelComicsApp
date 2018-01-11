package com.marvelcomics.brito.marvelcomics.ui;

import com.marvelcomics.brito.presentation.ResourceModel;

import javax.inject.Inject;

public class ResourceModelHandler {

    @Inject
    public ResourceModelHandler() {
    }

    public <T extends ResourceModel> void onModelChanged(T resourceModel, ResourceModelListener<T> listener) {
        if (resourceModel == null) {
            listener.onErrorState(new NullPointerException("ResourceModel returned as null"));
        } else {
            switch (resourceModel.getState()) {
                case SUCCESS:
                    listener.onSuccessState(resourceModel);
                    break;
                case LOADING:
                    listener.onLoadingState();
                    break;
                case ERROR:
                    listener.onErrorState(resourceModel.getThrowable());
                    break;
                default:
                    break;
            }
        }
    }

    public interface ResourceModelListener<T extends ResourceModel> {
        void onSuccessState(T resourceModel);
        void onErrorState(Throwable throwable);
        void onLoadingState();
    }
}
