package me.twc.source

/**
 * @author 唐万超
 * @date 2020/11/03
 */

/**
 * 代表资源获取的状态/结果
 */
sealed class Source<out T>

/**
 * 加载中资源
 */
object LoadingSource : Source<Nothing>()

/**
 * 失败资源
 */
data class ErrorSource(
    val message: String,
    val code: Int = SOURCE_CODE_UNKNOWN
) : Source<Nothing>()


/**
 * 成功资源
 */
data class SuccessSource<T>(
    val data: T
) : Source<T>()