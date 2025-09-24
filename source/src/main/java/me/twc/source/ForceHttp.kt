@file:Suppress("unused")

package me.twc.source

import kotlinx.coroutines.delay

/**
 * @author 唐万超
 * @date 2021/02/05
 */

suspend fun <T> forceHttpSource(
    delay: Long = 1000L,
    onError: ((source: ErrorSource) -> Unit)? = null,
    block: suspend () -> ISource<T>
): ResultSource<T> = forceHttp(runInIo = false, delay, onError, block)

suspend fun <T> forceHttpContext(
    delay: Long = 1000L,
    onError: ((source: ErrorSource) -> Unit)? = null,
    block: suspend () -> ISource<T>
): ResultSource<T> = forceHttp(runInIo = true, delay, onError, block)


/**
 * @param runInIo [true : 切换上下文到 IO],[false : 使用当前上下文]
 *
 * @param block 实际获取 ISource 的函数
 */
private suspend fun <T> forceHttp(
    runInIo: Boolean,
    delay: Long = 1000L,
    onError: ((source: ErrorSource) -> Unit)? = null,
    block: suspend () -> ISource<T>
): ResultSource<T> {
    while (true) {
        val source = if (runInIo) httpContext(block) else httpSource(block)
        if (source is SuccessSource<T>) {
            return source
        }
        if(onError != null && source is ErrorSource){
            onError(source)
        }
        delay(delay)
    }
}