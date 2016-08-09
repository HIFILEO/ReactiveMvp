package com.example.reactivemvp.dagger;

import com.example.reactivemvp.application.ReactiveMvpApplication;
import com.example.reactivemvp.viewcontroller.InfoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application-level Dagger2 {@link Component}.
 */
@Singleton
@Component(
        modules = {
                ApplicationModule.class,
        })
public interface ApplicationComponent {
    void inject(ReactiveMvpApplication application);
    void inject(InfoActivity infoActivity);
}
