package com.example.custombottomsheet.BottomSheet

import android.net.Uri

interface OnMultiImageSelectedListener : ImageListener {
    fun onImagesSelected(uriList: List<Uri>)
}