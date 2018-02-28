package co.midros.tmdb.utils.schedulers

import io.reactivex.Scheduler

/**
 * Created by luis rafael on 22/01/18.
 */
interface BaseSchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}