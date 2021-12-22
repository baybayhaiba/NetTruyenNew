package com.example.custombottomsheet

import androidx.fragment.app.FragmentActivity
import com.example.custombottomsheet.BottomSheet.BaseBuilder
import com.example.custombottomsheet.BottomSheet.BottomSheetFragment

class BottomSheetImage : BottomSheetFragment() {

    companion object {

        public fun with(fragmentActivity: FragmentActivity?): BaseBuilder {
            return BaseBuilder(fragmentActivity)
        }
    }
}