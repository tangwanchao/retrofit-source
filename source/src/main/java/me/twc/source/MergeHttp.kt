@file:Suppress("unused", "UNCHECKED_CAST")

package me.twc.source

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * @author 唐万超
 * @date 2021/02/02
 */
private fun <T> CoroutineScope.asyncIO(block: suspend () -> ISource<T>): Deferred<ResultSource<T>> =
    async(Dispatchers.IO) { block().toSource() }

/**
 * 合并 N 个 ISource 源获取
 * 注意: 调用此方法将丢失业务代码
 */
suspend fun <T> CoroutineScope.mergeNHttp(
    vararg methods: suspend () -> ISource<*>,
    block: (sources: List<*>) -> Source<T>
): Source<T> = withContext(Dispatchers.IO) {
    try {
        val deffereds = methods.map { asyncIO { it() } }
        // TODO 自定义 awaitAll,遇到 ErrorSource 直接完成
        val sources = awaitAll(*deffereds.toTypedArray())
        val errorSources = sources.filterIsInstance<ErrorSource>()
        if (errorSources.isNotEmpty()) {
            return@withContext errorSources.first()
        }
        // 如果代码走到这里,说明全是 SuccessSource
        return@withContext block(sources.map { it.data() })
    } catch (th: Throwable) {
        return@withContext th.toSource()
    }
}

suspend fun <A, B, R> CoroutineScope.merge2Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    block: (A, B) -> R
): Source<R> = mergeNHttp(
    method1, method2
) { sources ->
    val a = sources[0] as A
    val b = sources[1] as B
    return@mergeNHttp block(a, b).toSuccessSource()
}

suspend fun <A, B, C, R> CoroutineScope.merge3Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    block: (A, B, C) -> R
): Source<R> = mergeNHttp(
    method1, method2, method3
) { sources ->
    val a = sources[0] as A
    val b = sources[1] as B
    val c = sources[2] as C
    return@mergeNHttp block(a, b, c).toSuccessSource()
}

suspend fun <A, B, C, D, R> CoroutineScope.merge4Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    block: (A, B, C, D) -> R
): Source<R> = mergeNHttp(
    method1, method2, method3, method4
) { sources ->
    val a = sources[0] as A
    val b = sources[1] as B
    val c = sources[2] as C
    val d = sources[3] as D
    return@mergeNHttp block(a, b, c, d).toSuccessSource()
}

suspend fun <A, B, C, D, E, R> CoroutineScope.merge5Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    block: (A, B, C, D, E) -> R
): Source<R> = mergeNHttp(method1, method2, method3, method4, method5) { sources ->
    val a = sources[0] as A
    val b = sources[1] as B
    val c = sources[2] as C
    val d = sources[3] as D
    val e = sources[4] as E
    return@mergeNHttp block(a, b, c, d, e).toSuccessSource()
}

