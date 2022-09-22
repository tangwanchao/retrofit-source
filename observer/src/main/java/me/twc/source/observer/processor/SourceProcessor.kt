package me.twc.source.observer.processor

import me.twc.source.ErrorSource
import me.twc.source.LoadingSource
import me.twc.source.Source
import me.twc.source.SuccessSource
import me.twc.source.observer.ISourceObserverView
import me.twc.source.observer.widget.SourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 *
 * 配合 [SourceObserverView] 使用
 */
interface SourceProcessor {
    fun <T> process(view: ISourceObserverView, source: Source<T>): Pair<Boolean, T?>
}

object GlobalContentSourceProcessor : SourceProcessor {

    private var mLoading: LoadingSourceProcessor = SimpleLoadingSourceProcessor
    private var mError: ErrorSourceProcessor = SimpleContentErrorSourceProcessor
    private var mSuccess: SuccessSourceProcessor = SimpleSuccessSourceProcessor


    /**
     * 设置全局处理器
     */
    fun setProcessor(
        loading: LoadingSourceProcessor = mLoading,
        error: ErrorSourceProcessor = mError,
        success: SuccessSourceProcessor = mSuccess
    ) {
        mLoading = loading
        mError = error
        mSuccess = success
    }

    override fun <T> process(view: ISourceObserverView, source: Source<T>): Pair<Boolean, T?> {
        return when (source) {
            is LoadingSource -> mLoading.process<Nothing>(view, source)
            is ErrorSource -> mError.process<Nothing>(view, source)
            is SuccessSource -> mSuccess.process(view, source)
        }
    }
}

object GlobalLoadingSourceProcessor : SourceProcessor {

    private var mLoading: LoadingSourceProcessor = SimpleLoadingSourceProcessor
    private var mError: ErrorSourceProcessor = SimpleLoadingErrorSourceProcessor
    private var mSuccess: SuccessSourceProcessor = SimpleSuccessSourceProcessor

    /**
     * 设置全局处理器
     */
    fun setProcessor(
        loading: LoadingSourceProcessor = mLoading,
        error: ErrorSourceProcessor = mError,
        success: SuccessSourceProcessor = mSuccess
    ) {
        mLoading = loading
        mError = error
        mSuccess = success
    }

    override fun <T> process(view: ISourceObserverView, source: Source<T>): Pair<Boolean, T?> {
        return when (source) {
            is LoadingSource -> mLoading.process<Nothing>(view, source)
            is ErrorSource -> mError.process<Nothing>(view, source)
            is SuccessSource -> mSuccess.process(view, source)
        }
    }
}

class LocalSourceProcessor(
    private val mLoading: LoadingSourceProcessor? = null,
    private val mError: ErrorSourceProcessor? = null,
    private val mSuccess: SuccessSourceProcessor? = null
) : SourceProcessor {
    override fun <T> process(view: ISourceObserverView, source: Source<T>): Pair<Boolean, T?> {
        return when (source) {
            is LoadingSource -> mLoading?.process<Nothing>(view, source) ?: Pair(false, null)
            is ErrorSource -> mError?.process<Nothing>(view, source) ?: Pair(false, null)
            is SuccessSource -> mSuccess?.process(view, source) ?: Pair(false, null)
        }
    }
}

