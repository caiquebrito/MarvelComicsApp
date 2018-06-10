package com.marvelcomics.brito.presentation.presenter.home;

import android.os.Parcelable;

import com.marvelcomics.brito.entity.CharacterEntity;

import java.util.List;

public interface HomeContract {

    interface View {
        void showError(String message);
        void showCharacter(CharacterEntity character);
    }

    interface Presenter {
        void loadCharacter(String name);
    }
}
