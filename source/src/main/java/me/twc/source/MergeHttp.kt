@file:Suppress("unused")

package me.twc.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * @author 唐万超
 * @date 2021/02/02
 */

/**
 * 合并两个 ISource 资源获取
 */
suspend fun <A, B, R> merge2Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    block: (A, B) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData inner@{ b ->
            return@inner block(a, b).successSource()
        }
    }
}

/**
 * 合并三个 ISource 资源获取
 */
suspend fun <A, B, C, R> merge3Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    block: (A, B, C) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData inner@{ c ->
                return@inner block(a, b, c).successSource()
            }
        }
    }
}

/**
 * 合并四个 ISource 资源获取
 */
suspend fun <A, B, C, D, R> merge4Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    block: (A, B, C, D) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData inner@{ d ->
                    return@inner block(a, b, c, d).successSource()
                }
            }

        }
    }
}


/**
 * 合并五个 ISource 资源获取
 */
suspend fun <A, B, C, D, E, R> merge5Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    block: (A, B, C, D, E) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val deferredE = async(Dispatchers.IO) { httpSource { method5() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    val sourceE = deferredE.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData { d ->
                    sourceE.convertData inner@{ e ->
                        return@inner block(a, b, c, d, e).successSource()
                    }
                }
            }
        }
    }
}

