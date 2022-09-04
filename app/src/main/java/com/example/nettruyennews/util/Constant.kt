package com.example.nettruyennews.util

object Constant {


    lateinit var URL_ORIGINAL: String
    val URL_HOME get() = "$URL_ORIGINAL?page="
    val URL_RANK get() = "$URL_ORIGINAL/tim-truyen?status=-1&sort=10&page="


    const val VERSION = 1
    const val TAG = "app_nettruyen"
    const val URL_ORIGINAL_CUSTOM = "http://www.nettruyenme.com/"

    const val VALUE_PERMISSION = 1
    private const val PERMISSION_READ_EXTERNAL = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private const val PERMISSION_WRITE_EXTERNAL = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val ALL_PERMISSION_ACTIVITY: Array<String> =
        arrayOf(PERMISSION_READ_EXTERNAL, PERMISSION_WRITE_EXTERNAL)
}