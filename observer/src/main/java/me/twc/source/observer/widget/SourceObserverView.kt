package me.twc.source.observer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.LayoutRes
import com.github.nukc.stateview.StateView
import me.twc.source.Source
import me.twc.source.observer.processor.*

/**
 * @author 唐万超
 * @date 2022/06/20
 *
 * 1.子类可以在构造函数设置 [retryResource] [loadingResource] [emptyResource] 或者通过布局文件设置
 * 2.子类可能需要实现 [onLayoutInflated] 用来设置相关 view
 */
abstract class SourceObserverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : StateView(context, attrs, defStyleAttr) {

    private var mRetryFunction: Function<Unit>? = null

    init {
        onInflateListener = object : OnInflateListener {
            override fun onInflate(layoutResource: Int, view: View) {
                onLayoutInflated(layoutResource, view)
            }
        }
    }

    fun setRetryFunction(retryFunction: Function<Unit>) {
        mRetryFunction = retryFunction
    }

    fun getRetryFunction(): Function<Unit>? = mRetryFunction

    /**
     * [retryResource] [loadingResource] [emptyResource] 被填充时调用
     *
     * @param layoutResource 布局文件 id
     * @param layoutResourceView [layoutResource] 对应 view
     */
    protected abstract fun onLayoutInflated(
        @LayoutRes layoutResource: Int,
        layoutResourceView: View
    )

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