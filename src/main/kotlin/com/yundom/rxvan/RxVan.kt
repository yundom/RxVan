package com.yundom.rxvan

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * RxVan, A light event bus written in kotlin.
 */
class RxVan {
    companion object {
        private val instance: RxVan by lazy {
            synchronized(RxVan::class.java) {
                RxVan()
            }
        }

        /**
         * Get the instance of RxVan
         *
         * @return The instance of RxVan
         */
        fun get(): RxVan = instance
    }

    val bus: PublishSubject<Any> by lazy {
        PublishSubject.create<Any>()
    }

    /**
     * Publish an event to the event bus.
     *
     * **Usage:**
     * ``` kotlin
     * RxVan.get()
     *      .post(MyEventType())
     * ```
     *
     * @param event The event to be published.
     */
    fun post(event: Any) {
        bus.onNext(event)
    }

    /**
     * Start observing a particular type of event.
     *
     * **Usage:**
     * ``` kotlin
     * RxVan.get()
     *      .observe<MyEventType>()
     *      .subscribe { event ->
     *          // Do something on receiving MyEventType event.
     *      }
     * ```
     *
     * @param T The type of event to be observed.
     * @return The observable of the particular type of event.
     */
    inline fun <reified T : Any> observe(): Observable<T> = bus.ofType(T::class.java)
}