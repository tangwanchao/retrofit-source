package me.twc.source.observer.processor

import android.widget.Toast
import me.twc.source.ErrorSource
import me.twc.source.observer.widget.SourceObserverView

/**
 * @author 唐万超
 * @date 2022/06/21
 */
interface ErrorSourceProcessor {
    /**
     * @return [true : 已处理]
     *         [false: 未处理]
     */
    fun <T> process(view: SourceObserverView, error: ErrorSource): Pair<Boolean, T?>
}

object SimpleContentErrorSourceProcessor : ErrorSourceProcessor {
    override fun <T> process(view: SourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
        view.showRetry()
        return true to null
    }
}

object SimpleLoadingErrorSourceProcessor : ErrorSourceProcessor {
    override fun <T> process(view: SourceObserverView, error: ErrorSource): Pair<Boolean, T?> {
        view.showContent()
        Toast.makeText(view.context, error.message, Toast.LENGTH_LONG).show()
        return true to null
    }
}