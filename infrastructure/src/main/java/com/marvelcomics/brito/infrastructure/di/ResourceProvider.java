package com.marvelcomics.brito.infrastructure.di;

import android.support.annotation.StringRes;

public interface ResourceProvider {
    String getString(@StringRes int stringId);
    String getString(@StringRes int stringId, Object... params);
}
