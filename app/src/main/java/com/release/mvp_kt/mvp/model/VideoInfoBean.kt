package com.release.mvp_kt.mvp.model

data class VideoInfoBean(
    val collect: Boolean,
    val cover: String,
    val downloadSpeed: Int,
    val downloadStatus: Int,
    val isCollect: Boolean,
    val length: Int,
    val loadedSize: Int,
    val m3u8_url: String,
    val mp4_url: String,
    val ptime: String,
    val sectiontitle: String,
    val title: String,
    val totalSize: Int,
    val vid: String
) {
    override fun toString(): String {
        return "VideoInfoBean(collect=$collect, cover='$cover', downloadSpeed=$downloadSpeed, downloadStatus=$downloadStatus, isCollect=$isCollect, length=$length, loadedSize=$loadedSize, m3u8_url='$m3u8_url', mp4_url='$mp4_url', ptime='$ptime', sectiontitle='$sectiontitle', title='$title', totalSize=$totalSize, vid='$vid')"
    }
}
