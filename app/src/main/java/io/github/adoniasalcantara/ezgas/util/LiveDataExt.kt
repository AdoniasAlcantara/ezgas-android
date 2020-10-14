package io.github.adoniasalcantara.ezgas.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap

fun <F : Any, S: Any> combine(
    firstSource: LiveData<F>,
    secondSource: LiveData<S>
): LiveData<Pair<F, S>> = MediatorLiveData<Pair<F, S>>().also { mediator ->
    var firstValue: F? = null
    var secondValue: S? = null

    val dispatchWhenReady = {
        if (firstValue != null && secondValue != null) {
            mediator.value = firstValue!! to secondValue!!
        }
    }

    mediator.addSource(firstSource.distinctUntilChanged()) {
        firstValue = it
        dispatchWhenReady()
    }

    mediator.addSource(secondSource.distinctUntilChanged()) {
        secondValue = it
        dispatchWhenReady()
    }
}

fun <F : Any, S: Any, R> combineSwitchMap(
    firstSource: LiveData<F>,
    secondSource: LiveData<S>,
    transform: (F, S) -> LiveData<R>
) = combine(firstSource, secondSource).switchMap { (f, s) -> transform(f, s) }