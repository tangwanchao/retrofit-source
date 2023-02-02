package me.twc.source.download

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @author 唐万超
 * @date 2023/02/02
 */
@Suppress("MemberVisibilityCanBePrivate")
class Downloader(
    val context: Context,
    val callback: DownloadCallback
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun startDownload() = withContext(Dispatchers.IO) {
        var outputFile: File?
        var fos: FileOutputStream? = null
        var bs: InputStream? = null
        try {
            val responseBody = callback.genResponseBody()
            bs = responseBody.byteStream()
            outputFile = callback.genSaveFile() ?: genSaveFile()
            fos = FileOutputStream(outputFile)
            callback.onStart()
            val totalLength = responseBody.contentLength()
            var downloadLength = 0
            val buffer = ByteArray(8 * 1024)
            var bytes = bs.read(buffer)
            while (bytes >= 0) {
                fos.write(buffer, 0, bytes)
                downloadLength += bytes
                withContext(Dispatchers.Main){
                    callback.onDownload(downloadLength, totalLength)
                }
                bytes = bs.read(buffer)
            }
        } catch (th: Throwable) {
            outputFile = null
            callback.onError(th)
        } finally {
            // 使用 use 关闭流
            fos?.use { }
            bs?.use { }
        }
        return@withContext outputFile
    }

    private fun genSaveFile(): File {
        return File(context.cacheDir, "downloader_${System.currentTimeMillis()}.apk")
    }
}