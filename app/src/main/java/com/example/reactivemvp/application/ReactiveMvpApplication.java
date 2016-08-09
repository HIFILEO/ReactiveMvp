package com.example.reactivemvp.application;

import android.app.Application;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.reactivemvp.dagger.ApplicationComponent;
import com.example.reactivemvp.dagger.ApplicationModule;
import com.example.reactivemvp.dagger.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * This is the Reactive MVP application class for setting up Dagger 2 and Timber.
 */
public class ReactiveMvpApplication extends Application  {
    private static ReactiveMvpApplication reactiveMvpApplication;
    private ApplicationComponent component;

    public static ReactiveMvpApplication getInstance() {
        return reactiveMvpApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ReactiveMvpApplication.reactiveMvpApplication = this;
        setupComponent();
        setupTimber();
    }

    /**
     * Get the {@link ApplicationComponent}.
     *
     * @return The single {@link ApplicationComponent} object.
     */
    public ApplicationComponent getComponent() {
        return component;
    }

    /**
     * Setup the Timber logging tree.
     */
    void setupTimber() {
        Timber.plant(new CrashReportingTree());
    }

    /**
     * Setup the Dagger2 component graph.
     */
    @VisibleForTesting
    void setupComponent() {
        if (component == null) {

            component = DaggerApplicationComponent.builder()
                    .applicationModule(getApplicationModule())
                    .build();
            component.inject(this);
        } else {
            Log.d(ReactiveMvpApplication.class.getSimpleName(), "setupComponent() called.  ApplicationComponent already set.");
        }
    }

    /**
     * Get application module.
     * @return
     */
    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

}
