package me.twc.source.download

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.twc.source.BuildConfig

/**
 * @author 唐万超
 * @date 2021/01/27
 */
interface ProgressListener {
    fun onProgress(bytesWritten: Long, contentLength: Long)
}

class SimpleProgressListener(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<Int>
) : ProgressListener {

    private var mPreTime = 0L
    private var mComplete = false
    private var mIgnoreFirstComplete = false
    private val mLiveData = MutableLiveData<Int>()
    init {
        mLiveData.observe(lifecycleOwner,observer)
    }

    override fun onProgress(bytesWritten: Long, contentLength: Long) {
        if(BuildConfig.DEBUG && !mIgnoreFirstComplete){
            mIgnoreFirstComplete = bytesWritten == contentLength
            return
        }
        if (mComplete) return
        val percent = bytesWritten.toFloat() / contentLength.toFloat()
        if (percent == 1f) {
            mComplete = true
            return onProgress(100)
        }
        val currTime = System.currentTimeMillis()
        if (currTime - mPreTime >= 16L){
            onProgress((percent * 100).toInt())
            mPreTime = currTime
        }
    }

    private fun onProgress(percent: Int){
        mLiveData.postValue(percent)
    }
}