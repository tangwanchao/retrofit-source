package me.twc.source

/**
 * @author 唐万超
 * @date 2021/02/02
 */


/**
 * 数据实体需要实现该接口以便使用 [httpContext]
 *
 * 示例:
 *
 * ```
 * private const val ERR_NO_SUCCESS = 10000
 *
 * data class BaseBean<T>(
 *      val errNo: Int,
 *      val errMsg: String,
 *      val data: T? = null
 * ) : ISource<T> {
 *      fun isSuccess(): Boolean = errNo == ERR_NO_SUCCESS
 *
 *      override fun getSourceCode(): Int = errNo
 *      override fun getSourceMessage(): String = errMsg
 *      override fun getSourceData(): T? = data
 *      override fun toSource(): ResultSource<T> = innerToSource()
 * }
 *
 * private fun <T> BaseBean<T>.innerToSource(): ResultSource<T> {
 *     ...
 *     return SuccessSource or ErrorSource
 * }
 * ```
 *
 * @see httpContext
 */

const val SOURCE_CODE_UNKNOWN = Int.MIN_VALUE
const val SOURCE_MSG_UNKNOWN = ""

interface ISource<T> {
    fun getSourceCode(): Int = SOURCE_CODE_UNKNOWN
    fun getSourceMessage(): String = SOURCE_MSG_UNKNOWN
    fun getSourceData(): T?
    fun toSource(): ResultSource<T> {
        val code = getSourceCode()
        val message = getSourceMessage()
        val data = getSourceData()
        return data?.toSuccessSource(code) ?: message.toErrorSource(code)
    }
}