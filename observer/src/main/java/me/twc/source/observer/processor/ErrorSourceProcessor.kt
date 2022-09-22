package me.twc.source.observer.processor

import me.twc.source.ErrorSource
import me.twc.source.observer.ISourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 */
interface ErrorSourceProcessor {
    /**
     * @return [true : 已处理]
     *         [false: 未处理]
     */
    fun <T> process(view: ISourceObserverView, error: ErrorSource): Pair<Boolean, T?>
}

object SimpleContentErrorSourceProcessor : ErrorSourceProcessor {
    override fun <T> process(view: ISourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
        view.showSourceErrorView()
        return true to null
    }
}

object SimpleLoadingErrorSourceProcessor : ErrorSourceProcessor {
    override fun <T> process(view: ISourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
        view.showSourceSuccessView()
        return true to null
    }
}