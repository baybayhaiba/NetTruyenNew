package com.example.nettruyennews.util

object Constant {

    const val VERSION = 1
    const val URL_ORIGINAL = "http://www.nettruyenme.com/"
    const val URL_HOME = "$URL_ORIGINAL?page="
    const val URL_RANK = "$URL_ORIGINAL/tim-truyen?status=-1&sort=10&page="
    const val TAG = "app_nettruyen"


    const val VALUE_PERMISSION = 1
    private const val PERMISSION_READ_EXTERNAL = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private const val PERMISSION_WRITE_EXTERNAL = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val ALL_PERMISSION_ACTIVITY: Array<String> =
        arrayOf(PERMISSION_READ_EXTERNAL, PERMISSION_WRITE_EXTERNAL)
}