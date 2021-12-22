package com.example.nettruyennews.util

object Constant {
    const val VERSION = 1
    const val URL = "http://www.nettruyengo.com/?page="
    const val URL_ORIGNAL = "http://www.nettruyengo.com/"
    const val TEST = "huy123"

    const val VALUE_PERMISSION = 1
    private const val PERMISSION_READ_EXTERNAL = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private const val PERMISSION_WRITE_EXTERNAL = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val ALL_PERMISSION_ACTIVITY: Array<String> =
        arrayOf(PERMISSION_READ_EXTERNAL, PERMISSION_WRITE_EXTERNAL)
}