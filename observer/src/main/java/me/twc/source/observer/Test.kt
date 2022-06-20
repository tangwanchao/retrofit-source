package me.twc.source.observer

import me.twc.source.ErrorSource
import me.twc.source.Source

/**
 * @author 唐万超
 * @date 2022/06/20
 */
object Test {
    fun test(): Source<String> {
        return ErrorSource("测试异常")
    }
}