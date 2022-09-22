package me.twc.source.observer.processor

import me.twc.source.SuccessSource
import me.twc.source.observer.ISourceObserverView
import me.twc.source.observer.widget.SourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 */
interface SuccessSourceProcessor {
    /**
     * @return [true : 已处理]
     *         [false: 未处理]
     */
    fun <T> process(view: ISourceObserverView, source: SuccessSource<T>): Pair<Boolean, T?>
}

object SimpleSuccessSourceProcessor : SuccessSourceProcessor {
    override fun <T> process(
        view: ISourceObserverView,
        source: SuccessSource<T>
    ): Pair<Boolean, T?> {
        view.showSourceSuccessView()
        return true to source.data
    }
}