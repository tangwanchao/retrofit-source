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
 * 代表源加载中状态
 */
object LoadingSource : Source<Nothing>()

/**
 * 代表结果源
 */
sealed class ResultSource<out T> : Source<T>()

/**
 * 失败源
 *
 * @param message 用户可见消息
 * @param code 业务代码
 * @param exception ErrorSource 附带异常信息
 */
data class ErrorSource(
    val message: String,
    val code: Int? = null,
    val exception: Throwable? = null
) : ResultSource<Nothing>()


/**
 * 成功源
 *
 * @param data 业务数据
 * @param code 业务代码
 */
data class SuccessSource<T>(
    val data: T,
    val code: Int? = null
) : ResultSource<T>()