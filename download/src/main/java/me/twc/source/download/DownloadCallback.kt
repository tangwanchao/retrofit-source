package me.twc.source.download

import androidx.annotation.MainThread
import okhttp3.ResponseBody
import java.io.File

/**
 * @author 唐万超
 * @date 2023/02/02
 */
interface DownloadCallback {
    /**
     * 获取下载结果
     *
     *
     * 下载配置示例:
     *
     * @Streaming
     * @GET
     * suspend fun downloadWithUrl(
     *     @Url url: String
     * ): ResponseBody
     *
     */
    suspend fun genResponseBody(): ResponseBody

    /**
     * 下载后文件保存位置
     */
    fun genSaveFile(): File? = null

    /**
     * 开始下载
     */
    @MainThread
    fun onStart() {}

    /**
     * 下载中
     */
    @MainThread
    fun onDownload(downloadLength: Int, totalLength: Long) {}

    /**
     * 下载失败
     */
    fun onError(th: Throwable) {
        th.printStackTrace()
    }
}