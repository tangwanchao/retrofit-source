package me.twc.source.observer.processor

import me.twc.source.LoadingSource
import me.twc.source.observer.engine.ISourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 */
interface LoadingSourceProcessor {
    /**
     * @return [true : 已处理]
     *         [false: 未处理]
     */
    fun <T> process(view: ISourceObserverView, loading: LoadingSource): Pair<Boolean, T?>
}

object SimpleLoadingSourceProcessor : LoadingSourceProcessor {
    override fun <T> process(view: ISourceObserverView, loading: LoadingSource): Pair<Boolean, T?> {
        view.showSourceLoadingView()
        return true to null
    }
}