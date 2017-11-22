package com.marvelcomics.brito.marvelcomics.application.injection;

import android.app.Application;

import com.marvelcomics.brito.marvelcomics.application.MarvelApplication;
import com.marvelcomics.brito.marvelcomics.application.injection.module.ActivityBuilder;
import com.marvelcomics.brito.marvelcomics.application.injection.module.AppModule;
import com.marvelcomics.brito.marvelcomics.application.injection.module.FragmentBuilder;
import com.marvelcomics.brito.marvelcomics.application.injection.module.InfrastructureModule;
import com.marvelcomics.brito.marvelcomics.application.injection.module.RepositoryModule;
import com.marvelcomics.brito.marvelcomics.application.injection.module.WebServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                ActivityBuilder.class,
                FragmentBuilder.class,
                AppModule.class,
                WebServiceModule.class,
                InfrastructureModule.class,
                RepositoryModule.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MarvelApplication application);
}
