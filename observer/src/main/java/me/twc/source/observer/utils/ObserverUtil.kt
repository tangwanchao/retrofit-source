package me.twc.source.observer.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import me.twc.source.ErrorSource
import me.twc.source.Source
import me.twc.source.observer.processor.*
import me.twc.source.observer.widget.SourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 */
@MainThread
fun <T> LiveData<Source<T>>.contentObserver(
    owner: LifecycleOwner,
    view: SourceObserverView,
    processorList: MutableList<SourceProcessor> = mutableListOf(),
    block: (t: T) -> Unit
) {
    processorList.add(GlobalContentSourceProcessor)
    observe(owner) {
        view.observer(it, processorList)?.let(block)
    }
}

@MainThread
fun <T> LiveData<Source<T>>.contentObserverWithError(
    owner: LifecycleOwner,
    view: SourceObserverView,
    processError: (view: SourceObserverView, error: ErrorSource) -> Boolean,
    block: (t: T) -> Unit
) {
    val errorSourceProcessor = object : ErrorSourceProcessor {
        override fun <T> process(view: SourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
            return processError(view, error) to null
        }
    }
    val localSourceProcessor = LocalSourceProcessor(mError = errorSourceProcessor)
    contentObserver(owner, view, mutableListOf(localSourceProcessor), block)
}

@MainThread
fun <T> LiveData<Source<T>>.loadingObserver(
    owner: LifecycleOwner,
    view: SourceObserverView,
    processorList: MutableList<SourceProcessor> = mutableListOf(),
    block: (t: T) -> Unit
) {
    processorList.add(GlobalLoadingSourceProcessor)
    observe(owner) {
        view.observer(it, processorList)?.let(block)
    }
}

@MainThread
fun <T> LiveData<Source<T>>.loadingObserverWithError(
    owner: LifecycleOwner,
    view: SourceObserverView,
    processError: (view: SourceObserverView, error: ErrorSource) -> Boolean,
    block: (t: T) -> Unit
) {
    val errorSourceProcessor = object : ErrorSourceProcessor {
        override fun <T> process(view: SourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
            return processError(view, error) to null
        }
    }
    val localSourceProcessor = LocalSourceProcessor(mError = errorSourceProcessor)
    loadingObserver(owner, view, mutableListOf(localSourceProcessor), block)
}
