package me.twc.source.engine

import me.twc.source.Source

/**
 * @author 唐万超
 * @date 2021/06/25
 *
 * 观察 [Source],做出不同响应
 */
interface ISourceObserver {
    fun <T> observerSource(source:Source<T>):T?
}