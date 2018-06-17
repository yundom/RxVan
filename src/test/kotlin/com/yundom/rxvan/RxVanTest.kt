package com.yundom.rxvan

import io.reactivex.observers.TestObserver
import org.junit.Test

class RxVanTest {

    data class MyEventType(val data: String)

    @Test
    fun testReceiveSingleEvent() {
        val event = MyEventType("Hello RxVan")
        val testObserver = TestObserver<MyEventType>()
        RxVan.get().observe<MyEventType>().subscribe(testObserver)

        RxVan.get().post(event)

        testObserver.assertSubscribed()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.data == "Hello RxVan"
        }
    }

    @Test
    fun testReceiveMultipleEvent() {
        val rounds = 100
        val events = mutableListOf<MyEventType>()
        (0 until rounds).forEach {
            events.add(MyEventType(it.toString()))
        }
        val testObserver = TestObserver<MyEventType>()
        RxVan.get().observe<MyEventType>().subscribe(testObserver)

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
}