@file:Suppress("unused")

package me.twc.source

/**
 * @author 唐万超
 * @date 2021/02/02
 */

/**
 * 将任意对象转换为一个成功的源
 */
fun <T> T.toSuccessSource(code: Int? = null): SuccessSource<T> {
    return SuccessSource(this, code)
}

/**
 * 将字符串转为失败源
 */
fun String.toErrorSource(
    code: Int? = null,
    th: Throwable? = null
): ErrorSource {
    return ErrorSource(this, code ?: SOURCE_CODE_UNKNOWN, th)
}

/**
 * 转换源数据
 */
fun <T, R> Source<T>.map(block: (T) -> R): Source<R> {
    return when (this) {
        is LoadingSource -> this
        is ErrorSource -> this
        is SuccessSource -> SuccessSource(block(data))
    }
}

/**
 * 合并源数据
 */
inline fun <A, B, R> Source<A>.zip(
    other: Source<B>,
    combine: (A, B) -> R
): Source<R> = when {
    this is ErrorSource -> this
    other is ErrorSource -> other
    this is SuccessSource && other is SuccessSource -> SuccessSource(combine(this.data, other.data))
    else -> LoadingSource
}

/**
 * 强制获取源的业务代码,可能抛出异常
 */
fun Source<*>.code(): Int{
    return this.codeOrNull()!!
}

/**
 * 强制获取源的数据,可能抛出异常
 */
fun <T> Source<T>.data(): T {
    return this.dataOrNull()!!
}

/**
 * 尝试获取源的业务代码
 */
fun Source<*>.codeOrNull(): Int?{
    return when(this){
        is SuccessSource<*> -> this.code
        else -> null
    }
}

/**
 * 尝试获取源的数据
 */
fun <T> Source<T>.dataOrNull(): T? {
    return when (this) {
        is SuccessSource -> this.data
        else -> null
    }
}