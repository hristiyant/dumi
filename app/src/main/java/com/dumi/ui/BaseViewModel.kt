package com.dumi.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.dumi.event.LiveEvent
import com.dumi.event.LiveEventMap
import com.dumi.networking.service.WordsApiService
import com.dumi.util.RxUtils
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var wordsApiService: WordsApiService

    private var compositeDisposable: CompositeDisposable? = null

    private val liveEventMap = LiveEventMap()

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

    protected fun <T> subscribeSingle(
        observable: Single<T>,
        onSuccess: Consumer<T>,
        onError: Consumer<Throwable>
    ) =
        getCompositeDisposable().add(
            observable.compose(RxUtils.applySingleSchedulers()).subscribe(
                onSuccess,
                onError
            )
        )

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }

        return compositeDisposable as CompositeDisposable
    }

    override fun onCleared() {
        getCompositeDisposable().dispose()
        super.onCleared()
    }
}