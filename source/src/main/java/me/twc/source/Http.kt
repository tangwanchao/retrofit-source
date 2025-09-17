package me.twc.source

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.twc.source.util.logD
import retrofit2.HttpException

/**
 * @author 唐万超
 * @date 2021/02/02
 */


/**
 * 使用 IO 线程获取 Source
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
): ResultSource<T> = withContext(Dispatchers.IO) {
    return@withContext httpSource(block)
}

/**
 * 使用调用线程获取 Source
 */
suspend fun <T> httpSource(
    block: suspend () -> ISource<T>
): ResultSource<T> = try {
    block().toSource()
} catch (th: Throwable) {
    th.toSource()
}

fun <T> Throwable.toSource(): ResultSource<T> {
    logD("RetrofitSource Throwable.toSource", th = this)
    return when (this) {
        is HttpException -> {
            if (this.code() in 400 until 500) {
                "客户端异常"
            } else {
                "服务端异常"
            }
        }

        is KotlinNullPointerException,
        is JsonParseException,
        is MalformedJsonException -> "服务端数据异常"

        is CancellationException -> ""
        else -> "网络异常"
    }.toErrorSource(th = this)
}