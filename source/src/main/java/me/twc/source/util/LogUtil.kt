package me.twc.source.util

import android.util.Log

/**
 * @author 唐万超
 * @date 2021/04/12
 */
private const val TAG = "RetrofitSource"

var SOURCE_LOG_ENABLE = false

fun canLog(level: Int): Boolean {
    return SOURCE_LOG_ENABLE || Log.isLoggable("RetrofitSource", level)
}

fun logD(message: String, th: Throwable? = null) {
    if (canLog(Log.DEBUG)) {
        Log.d(TAG, message, th)
    }
}