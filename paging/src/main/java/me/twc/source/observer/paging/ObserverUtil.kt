package me.twc.source.observer.paging

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import me.twc.source.ErrorSource
import me.twc.source.LoadingSource
import me.twc.source.Source
import me.twc.source.SuccessSource
import me.twc.source.observer.engine.ISourceObserverView

/**
 * 配合 [SmartRefreshLayout] 刷新观察
 */
fun <T> LiveData<Source<IPagination<T>>>.paginationRefreshObserver(
    owner: LifecycleOwner,
    refreshLayout: SmartRefreshLayout,
    observerView: ISourceObserverView,
    block: (list: List<T>) -> Unit
) = this.observe(owner) { source ->
    when (source) {
        LoadingSource -> {
            refreshLayout.autoRefreshAnimationOnly()
            refreshLayout.setEnableLoadMore(false)
        }
        is ErrorSource -> {
            refreshLayout.finishRefresh(false)
            observerView.showSourceErrorView()
        }
        is SuccessSource -> {
            val pagination = source.data
            refreshLayout.finishRefresh(0, true, pagination.noMoreData())
            val data = pagination.getPagingDataList()
            if (data.isEmpty()) {
                observerView.showSourceEmptyView()
            } else {
                refreshLayout.setEnableLoadMore(true)
                observerView.showSourceSuccessView()
                block(data)
            }
        }
    }
}

/**
 * 配合 [SmartRefreshLayout] 加载更多观察
 */
fun <T> LiveData<Source<IPagination<T>>>.paginationLoadMoreObserver(
    owner: LifecycleOwner,
    refreshLayout: SmartRefreshLayout,
    block: (list: List<T>) -> Unit
) = this.observe(owner) { source ->
    when (source) {
        LoadingSource -> Unit
        is ErrorSource -> {
            refreshLayout.finishLoadMore(0, false, false)
        }
        is SuccessSource -> {
            val pagination = source.data
            refreshLayout.finishLoadMore(0, true, pagination.noMoreData())
            block(pagination.getPagingDataList())
        }
    }
}