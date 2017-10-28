package com.tronography.pixelweather;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;


public class RxImmediateSchedulerRule implements TestRule {

    private final Scheduler immediateScheduler = new Scheduler() {
        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }
    };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediateScheduler);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}

