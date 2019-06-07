package com.dumi.ui

import androidx.lifecycle.*
import com.dumi.event.LiveEvent
import com.dumi.event.LiveEventMap
import kotlin.reflect.KClass

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val liveEventMap= LiveEventMap()

//    var loadingState = MutableLiveData<LoadingState>().apply { value = Normal }

    fun <T : LiveEvent> subscribe(
        lifecycleOwner: LifecycleOwner,
        eventClass: KClass<T>,
        eventObserver: Observer<T>
    ) {
        liveEventMap.subscribe(lifecycleOwner, eventClass, eventObserver)
    }

    protected fun <T : LiveEvent> publish(event: T) {
        liveEventMap.publish(event)
    }
}