package me.twc.source.observer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.LayoutRes
import com.github.nukc.stateview.StateView
import me.twc.source.observer.engine.ISourceObserverView

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
) : StateView(context, attrs, defStyleAttr), ISourceObserverView {

    private var mRetryFunction: (() -> Unit)? = null

    init {
        onInflateListener = object : OnInflateListener {
            override fun onInflate(layoutResource: Int, view: View) {
                onLayoutInflated(layoutResource, view)
            }
        }
    }

    fun setRetryFunction(retryFunction: (() -> Unit)?) {
        mRetryFunction = retryFunction
    }

    fun getRetryFunction(): (() -> Unit)? = mRetryFunction

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

    //<editor-fold desc="ISourceObserverView">
    override fun showSourceLoadingView() {
        showLoading()
    }

    override fun showSourceErrorView() {
        showRetry()
    }

    override fun showSourceSuccessView() {
        showContent()
    }
    //</editor-fold>
}