@file:Suppress("unused")

package me.twc.source

import kotlinx.coroutines.delay

/**
 * @author 唐万超
 * @date 2021/02/05
 */

suspend fun <T> forceHttpSource(
    block: suspend () -> ISource<T>
): Source<T> = forceHttp(runInIo = false, block)

suspend fun <T> forceHttpContext(
    block: suspend () -> ISource<T>
): Source<T> = forceHttp(runInIo = true, block)


/**
 * @param runInIo [true : 切换上下文到 IO],[false : 使用当前上下文]
 *
 * @param block 实际获取 ISource 的函数
 */
private suspend fun <T> forceHttp(
    runInIo: Boolean,
    block: suspend () -> ISource<T>
): Source<T> {
    while (true) {
        val source = if (runInIo) httpSource(block) else httpContext(block)
        if (source is SuccessSource<T>) {
            return source
        }
        delay(1_000L)
    }
}