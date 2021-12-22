package com.example.custombottomsheet.BottomSheet

import androidx.fragment.app.FragmentActivity
import com.example.myfile.adapter.Photo

open class BaseBuilder constructor(val fragmentActivity: FragmentActivity?) {
    var textDone: String? = null
    var peekHeight: Int = Int.MAX_VALUE
    var images = mutableListOf<Photo>()
    var imageListener: ImageListener? = null

    fun observerImage(register: ImageListener?): BottomSheetFragment {
        this.imageListener = register
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.builder = this
        return bottomSheetFragment
    }

    fun setPeekHeight(peekHeight: Int): BaseBuilder {
        this.peekHeight = peekHeight
        return this
    }

    fun setTitleDone(text: String): BaseBuilder {
        textDone = text
        return this
    }

    fun onClickOnceImage(imageListener: OnImageSelectedListener) {
        if (fragmentActivity != null) {
            observerImage(imageListener)
                .show(fragmentActivity.supportFragmentManager, null)
        }
    }

    fun onClickMultiImage(imageListener: OnMultiImageSelectedListener) {
        if (fragmentActivity != null) {
            observerImage(imageListener)
                .show(fragmentActivity.supportFragmentManager, null)
        }
    }


}