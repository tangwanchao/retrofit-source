package me.twc.source.observer.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.view.isVisible
import me.twc.source.observer.R
import me.twc.source.observer.engine.ISourceObserverView

/**
 * @author 唐万超
 * @date 2022/09/23
 */
open class SourceObserverView2 @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), ISourceObserverView {
    companion object {
        /**
         * 展示实际 Loading 的延迟时间
         */
        val GLOBAL_LOADING_DELAY_TIME = 450L
        val GLOBAL_ERROR_BACKGROUND = Color.WHITE
        val GLOBAL_EMPTY_BACKGROUND = Color.WHITE
    }

    var mLoadingDelayTime = GLOBAL_LOADING_DELAY_TIME
    var mErrorBackground = GLOBAL_ERROR_BACKGROUND
    var mEmptyBackground = GLOBAL_EMPTY_BACKGROUND
    var mReloadFun: (() -> Unit)? = null

    private val mLayoutInflater = LayoutInflater.from(context)
    private var mLoadingLayout: View? = null
    private var mErrorLayout: View? = null
    private var mEmptyLayout: View? = null

    private val mDelayLoadingRunnable = Runnable {
        mLoadingLayout?.isVisible = true
    }

    init {
        // 可点击,防止点击穿透
        isClickable = true
        isVisible = false
    }

    @LayoutRes
    open fun getLoadingLayoutRes(): Int = R.layout.source_observer_layout_loading

    @LayoutRes
    open fun getErrorLayoutRes(): Int = R.layout.source_observer_layout_error

    @LayoutRes
    open fun getEmptyLayoutRes(): Int = R.layout.source_observer_layout_empty

    @MainThread
    override fun showSourceLoadingView() {
        if (mLoadingLayout == null) {
            mLoadingLayout = mLayoutInflater.inflate(getLoadingLayoutRes(), this, false)
            addView(mLoadingLayout)
        }
        showView()
        postDelayed(mDelayLoadingRunnable, mLoadingDelayTime)
    }

    @MainThread
    override fun showSourceErrorView(message: String) {
        if (mErrorLayout == null) {
            mErrorLayout = mLayoutInflater.inflate(getErrorLayoutRes(), this, false)
            mErrorLayout!!.setBackgroundColor(mErrorBackground)
            mErrorLayout!!.setOnClickListener {
                mReloadFun?.invoke()
            }
            addView(mErrorLayout)
        }
        mErrorLayout!!.findViewById<TextView>(R.id.tv_network_error).text = message
        showView(mErrorLayout)
    }

    @MainThread
    override fun showSourceEmptyView(message: String) {
        if (mEmptyLayout == null) {
            mEmptyLayout = mLayoutInflater.inflate(getEmptyLayoutRes(), this, false)
            mEmptyLayout!!.setBackgroundColor(mEmptyBackground)
            addView(mEmptyLayout)
        }
        mEmptyLayout!!.findViewById<TextView>(R.id.tv_empty).text = message
        showView(mEmptyLayout)
    }

    @MainThread
    override fun showSourceSuccessView() {
        removeCallbacks(mDelayLoadingRunnable)
        isVisible = false
    }

    private fun showView(show: View? = null) {
        removeCallbacks(mDelayLoadingRunnable)
        mLoadingLayout?.isVisible = show === mLoadingLayout
        mErrorLayout?.isVisible = show === mErrorLayout
        mEmptyLayout?.isVisible = show === mEmptyLayout
        isVisible = true
    }
}