package com.example.reactivemvp;

import android.support.annotation.CallSuper;
import android.support.annotation.VisibleForTesting;

import com.example.reactivemvp.categories.UnitTest;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

/**
 * Base class for RX Testing.
 */
@Category(UnitTest.class)
public abstract class RxBaseTest {
    @VisibleForTesting
    protected TestScheduler testScheduler;

    @Before
    @CallSuper
    public void setUp() {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
        testScheduler = Schedulers.test();
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
                                         @Override
                                         public Scheduler call(Scheduler scheduler) {
                                             return RxBaseTest.this.testScheduler;
                                         }
                                     });
        RxJavaHooks.setOnComputationScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return RxBaseTest.this.testScheduler;
            }
        });
        RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return RxBaseTest.this.testScheduler;
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new TestAndroidSchedulersHook(Schedulers.immediate()));
    }

    @After
    @CallSuper
    public void tearDown() {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    static public class TestAndroidSchedulersHook extends RxAndroidSchedulersHook {

        private final Scheduler scheduler;

        public TestAndroidSchedulersHook(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public Scheduler getMainThreadScheduler() {
            return scheduler;
        }
    }
}
