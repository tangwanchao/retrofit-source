package me.twc.source

/**
 * @author 唐万超
 * @date 2020/11/03
 */

const val SOURCE_CODE_UNKNOWN = Int.MIN_VALUE
const val SOURCE_MSG_UNKNOWN = ""

sealed class Source<out T>

object LoadingSource : Source<Nothing>()

data class ErrorSource(
    val message: String,
    val code: Int = Int.MIN_VALUE
) : Source<Nothing>()

data class SuccessSource<T>(
    val data: T
) : Source<T>()