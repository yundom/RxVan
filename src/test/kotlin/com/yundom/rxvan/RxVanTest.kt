package com.yundom.rxvan

import io.reactivex.observers.TestObserver
import org.junit.Test

class RxVanTest {

    data class MyEventType01(val data: String)
    data class MyEventType02(val data: String)
    data class MyEventType03(val data: String)

    @Test
    fun testReceiveSingleEvent() {
        val event = MyEventType01("Hello RxVan")
        val testObserver = TestObserver<MyEventType01>()
        RxVan.get().observe<MyEventType01>().subscribe(testObserver)

        RxVan.get().post(event)

        testObserver.assertSubscribed()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.data == "Hello RxVan"
        }
    }

    @Test
    fun testReceiveMultipleEvents() {
        val rounds = 100
        val events = mutableListOf<MyEventType01>()
        (0 until rounds).forEach {
            events.add(MyEventType01(it.toString()))
        }
        val testObserver = TestObserver<MyEventType01>()
        RxVan.get().observe<MyEventType01>().subscribe(testObserver)

        events.forEach {
            RxVan.get().post(it)
        }

        testObserver.assertSubscribed()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(100)
        (0 until rounds).forEach { round ->
            testObserver.assertValueAt(round) {
                it.data == events[round].data
            }
        }
    }

    @Test
    fun testReceiveMultipleTypesOfEvent() {
        val event01 = MyEventType01("Hello Event 01")
        val testObserverOfEventType01 = TestObserver<MyEventType01>()
        RxVan.get().observe<MyEventType01>().subscribe(testObserverOfEventType01)

        val event02 = MyEventType02("Hello Event 02")
        val testObserverOfEventType02 = TestObserver<MyEventType02>()
        RxVan.get().observe<MyEventType02>().subscribe(testObserverOfEventType02)

        val event03 = MyEventType03("Hello Event 03")
        val testObserverOfEventType03 = TestObserver<MyEventType03>()
        RxVan.get().observe<MyEventType03>().subscribe(testObserverOfEventType03)

        RxVan.get().post(event01)
        RxVan.get().post(event02)
        RxVan.get().post(event03)

        testObserverOfEventType01.assertSubscribed()
        testObserverOfEventType01.assertNoErrors()
        testObserverOfEventType01.assertValueCount(1)
        testObserverOfEventType01.assertValue {
            it.data == event01.data
        }
        testObserverOfEventType02.assertSubscribed()
        testObserverOfEventType02.assertNoErrors()
        testObserverOfEventType02.assertValueCount(1)
        testObserverOfEventType02.assertValue {
            it.data == event02.data
        }
        testObserverOfEventType03.assertSubscribed()
        testObserverOfEventType03.assertNoErrors()
        testObserverOfEventType03.assertValueCount(1)
        testObserverOfEventType03.assertValue {
            it.data == event03.data
        }
    }
}