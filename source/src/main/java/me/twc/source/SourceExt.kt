@file:Suppress("unused")

package me.twc.source

/**
 * @author 唐万超
 * @date 2021/02/02
 */
fun <T> T.successSource(): SuccessSource<T> {
    return SuccessSource(this)
}

fun String.errorSource(): ErrorSource {
    return ErrorSource(this)
}

fun <T, R> Source<T>.convertData(block: (T) -> Source<R>): Source<R> {
    return when (this) {
        LoadingSource -> LoadingSource
        is ErrorSource -> ErrorSource(message, code)
        is SuccessSource -> block(data)
    }
}

fun <T> Source<T>.dataElseNull(): T? {
    return when (this) {
        is SuccessSource -> this.data
        else -> null
    }
}