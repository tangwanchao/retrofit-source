package me.twc.source.engine

import me.twc.source.*

/**
 * @author 唐万超
 * @date 2021/06/25
 *
 * 根据不同的 [Source] 展示不同的 UI 状态
 */
interface UISourceObserver : ISourceObserver {
    override fun <T> observerSource(source: Source<T>): T? {
        when (source) {
            LoadingSource -> showLoadingSourceView()
            is ErrorSource -> showErrorSourceView(source)
            is SuccessSource -> showSuccessSourceView()
        }
        return source.dataElseNull()
    }

    fun showLoadingSourceView() = Unit
    fun showErrorSourceView(errorSource: ErrorSource) = Unit
    fun showSuccessSourceView() = Unit
}