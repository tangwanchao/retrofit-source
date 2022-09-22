package me.twc.source.observer

import me.twc.source.Source
import me.twc.source.observer.processor.SourceProcessor

/**
 * @author 唐万超
 * @date 2022/09/22
 */
interface ISourceObserverView {
    /**
     * 展示 loading ui
     */
    fun showSourceLoadingView()

    /**
     * 展示异常 ui
     */
    fun showSourceErrorView()

    /**
     * 展示成功 ui
     */
    fun showSourceSuccessView()

    /**
     * 观察 source 并展示相应 ui
     */
    fun <T> observer(
        source: Source<T>,
        processorList: MutableList<SourceProcessor> = mutableListOf()
    ): T? {
        for (processor in processorList) {
            val (success, t) = processor.process(this, source)
            if (success) {
                return t
            }
        }
        return null
    }
}