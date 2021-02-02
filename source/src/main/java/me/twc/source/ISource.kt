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
 * private const val ERR_NO_INIT = Int.MIN_VALUE
 *
 * private const val ERR_MSG_INIT = ""
 *
 * data class BaseBean<T>(
 *      val errNo: Int = ERR_NO_INIT,
 *      val errMsg: String = ERR_MSG_INIT,
 *      val data: T? = null
 * ) : ISource<T> {
 *      fun isSuccess(): Boolean = errNo == ERR_NO_SUCCESS
 *
 *      override fun getSourceCode(): Int = errNo
 *      override fun getSourceMsg(): String = errMsg
 *      override fun getSourceData(): T? = data
 *      override fun toSource(): Source<T> = innerToSource()
 * }
 *
 * private fun <T> BaseBean<T>.innerToSource(): Source<T> {
 *      val data = data
 *      if (isSuccess() && data != null) {
 *          return data.bangSuccess()
 *      }
 *      return BangError(errMsg, errNo)
 * }
 * ```
 *
 * @see httpContext
 */

const val SOURCE_CODE_UNKNOWN = Int.MIN_VALUE
const val SOURCE_MSG_UNKNOWN = ""

interface ISource<T> {
    fun getSourceCode(): Int = SOURCE_CODE_UNKNOWN
    fun getSourceMsg(): String = SOURCE_MSG_UNKNOWN
    fun getSourceData(): T?
    fun toSource(): Source<T> {
        return getSourceData()?.successSource() ?: ErrorSource(getSourceMsg(), getSourceCode())
    }
}