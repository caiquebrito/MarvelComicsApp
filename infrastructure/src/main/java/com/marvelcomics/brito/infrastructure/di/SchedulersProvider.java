package com.marvelcomics.brito.infrastructure.di;

import io.reactivex.Scheduler;

public interface SchedulersProvider {

    Scheduler computation();

    Scheduler main();
}
