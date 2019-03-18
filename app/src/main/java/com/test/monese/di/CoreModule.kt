package com.test.monese.di

import com.test.data.util.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module

/**
 * Core Module will contain classes for generic use.
 */
val coreModule = module {

    single("IO_SCHEDULER") { Schedulers.io() }
    single("MAIN_THREAD_SCHEDULER") { AndroidSchedulers.mainThread() }

    single {
        SchedulerProvider(
            backgroundScheduler = get("IO_SCHEDULER"),
            foregroundScheduler = get("MAIN_THREAD_SCHEDULER")
        )
    }

}
