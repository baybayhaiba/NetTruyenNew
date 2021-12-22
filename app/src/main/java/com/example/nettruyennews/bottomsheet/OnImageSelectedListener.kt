package com.example.custombottomsheet.BottomSheet

import android.net.Uri

interface OnImageSelectedListener : ImageListener {
    fun onImageSelected(uri: Uri)
}