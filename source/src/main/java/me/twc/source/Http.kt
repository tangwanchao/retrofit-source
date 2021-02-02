package me.twc.source

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * @author 唐万超
 * @date 2021/02/02
 */


/**
 * 使用 IO 线程获取 ISource
 *
 * Retrofit 使用示例:
 *
 * ```
 * lifecycleScope.launch {
 *      // UI 线程获取数据
 *      val data = httpContext {
 *              WeHttpService.getPictureList()
 *      }
 *      // 主线程更新 UI
 * }
 * ```
 */
suspend fun <T> httpContext(
    block: suspend () -> ISource<T>
): Source<T> = withContext(Dispatchers.IO) {
    return@withContext httpSource(block)
}

/**
 * 使用调用线程获取 ISource
 */
suspend fun <T> httpSource(
    block: suspend () -> ISource<T>
): Source<T> = try {
    block.invoke().toSource()
} catch (th: Throwable) {
    th.toSource()
}

private fun <T> Throwable.toSource(): Source<T> = when (this) {
    is HttpException -> {
        if (this.code() in 400 until 500) {
            "客户端异常".errorSource()
        } else {
            "服务端异常".errorSource()
        }
    }
    is KotlinNullPointerException,
    is JsonParseException,
    is MalformedJsonException -> {
        "服务端数据异常".errorSource()
    }
    else -> {
        "网络异常".errorSource()
    }
}