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

/**
 * 合并六个 ISource 资源获取
 */
suspend fun <A, B, C, D, E, F, R> merge6Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    method6: suspend () -> ISource<F>,
    block: (A, B, C, D, E, F) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val deferredE = async(Dispatchers.IO) { httpSource { method5() } }
    val deferredF = async(Dispatchers.IO) { httpSource { method6() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    val sourceE = deferredE.await()
    val sourceF = deferredF.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData { d ->
                    sourceE.convertData { e ->
                        sourceF.convertData inner@{ f ->
                            return@inner block(a, b, c, d, e, f).successSource()
                        }
                    }
                }
            }
        }
    }
}

/**
 * 合并七个 ISource 资源获取
 */
suspend fun <A, B, C, D, E, F, G, R> merge7Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    method6: suspend () -> ISource<F>,
    method7: suspend () -> ISource<G>,
    block: (A, B, C, D, E, F, G) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val deferredE = async(Dispatchers.IO) { httpSource { method5() } }
    val deferredF = async(Dispatchers.IO) { httpSource { method6() } }
    val deferredG = async(Dispatchers.IO) { httpSource { method7() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    val sourceE = deferredE.await()
    val sourceF = deferredF.await()
    val sourceG = deferredG.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData { d ->
                    sourceE.convertData { e ->
                        sourceF.convertData { f ->
                            sourceG.convertData inner@{ g ->
                                return@inner block(a, b, c, d, e, f, g).successSource()
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 合并八个 ISource 资源获取
 */
suspend fun <A, B, C, D, E, F, G, H, R> merge8Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    method6: suspend () -> ISource<F>,
    method7: suspend () -> ISource<G>,
    method8: suspend () -> ISource<H>,
    block: (A, B, C, D, E, F, G, H) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val deferredE = async(Dispatchers.IO) { httpSource { method5() } }
    val deferredF = async(Dispatchers.IO) { httpSource { method6() } }
    val deferredG = async(Dispatchers.IO) { httpSource { method7() } }
    val deferredH = async(Dispatchers.IO) { httpSource { method8() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    val sourceE = deferredE.await()
    val sourceF = deferredF.await()
    val sourceG = deferredG.await()
    val sourceH = deferredH.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData { d ->
                    sourceE.convertData { e ->
                        sourceF.convertData { f ->
                            sourceG.convertData { g ->
                                sourceH.convertData inner@{ h ->
                                    return@inner block(a, b, c, d, e, f, g, h).successSource()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 合并九个 ISource 资源获取
 */
suspend fun <A, B, C, D, E, F, G, H, I, R> merge9Http(
    method1: suspend () -> ISource<A>,
    method2: suspend () -> ISource<B>,
    method3: suspend () -> ISource<C>,
    method4: suspend () -> ISource<D>,
    method5: suspend () -> ISource<E>,
    method6: suspend () -> ISource<F>,
    method7: suspend () -> ISource<G>,
    method8: suspend () -> ISource<H>,
    method9: suspend () -> ISource<I>,
    block: (A, B, C, D, E, F, G, H, I) -> R
): Source<R> = coroutineScope {
    val deferredA = async(Dispatchers.IO) { httpSource { method1() } }
    val deferredB = async(Dispatchers.IO) { httpSource { method2() } }
    val deferredC = async(Dispatchers.IO) { httpSource { method3() } }
    val deferredD = async(Dispatchers.IO) { httpSource { method4() } }
    val deferredE = async(Dispatchers.IO) { httpSource { method5() } }
    val deferredF = async(Dispatchers.IO) { httpSource { method6() } }
    val deferredG = async(Dispatchers.IO) { httpSource { method7() } }
    val deferredH = async(Dispatchers.IO) { httpSource { method8() } }
    val deferredI = async(Dispatchers.IO) { httpSource { method9() } }
    val sourceA = deferredA.await()
    val sourceB = deferredB.await()
    val sourceC = deferredC.await()
    val sourceD = deferredD.await()
    val sourceE = deferredE.await()
    val sourceF = deferredF.await()
    val sourceG = deferredG.await()
    val sourceH = deferredH.await()
    val sourceI = deferredI.await()
    return@coroutineScope sourceA.convertData { a ->
        sourceB.convertData { b ->
            sourceC.convertData { c ->
                sourceD.convertData { d ->
                    sourceE.convertData { e ->
                        sourceF.convertData { f ->
                            sourceG.convertData { g ->
                                sourceH.convertData { h ->
                                    sourceI.convertData inner@{ i ->
                                        return@inner block(
                                            a,
                                            b,
                                            c,
                                            d,
                                            e,
                                            f,
                                            g,
                                            h,
                                            i
                                        ).successSource()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

