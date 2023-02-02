package me.twc.source.download

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*

/**
 * @author 唐万超
 * @date 2021/01/27
 */
class ProgressRequestBody(
    private val mDelegate: RequestBody,
    private val mListener: ProgressListener
) : RequestBody() {

    override fun contentType(): MediaType? {
        return mDelegate.contentType()
    }

    override fun contentLength(): Long {
        return try {
            mDelegate.contentLength()
        } catch (ioe: IOException) {
            -1
        }

    }

    override fun writeTo(sink: BufferedSink) {
        val progressSink = ProgressSink(sink)
        val bufferSink = progressSink.buffer()
        mDelegate.writeTo(bufferSink)
        bufferSink.flush()
    }


    private inner class ProgressSink(delegate:Sink): ForwardingSink(delegate){

        private var bytesWritten:Long = 0L

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            mListener.onProgress(bytesWritten, contentLength())
        }
    }
}
