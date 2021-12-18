package com.example.nettruyennews.extension

import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

enum class DIRECTION {
    UP, DOWN, NOTSCROLL
}


fun RecyclerView.direction(listener: (direction: DIRECTION) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            Log.d("scroll view", "onScrolled: ${dy}")

            if (dy > 0) {
                listener(DIRECTION.UP)
            } else if (dy < 0) {
                listener(DIRECTION.DOWN)
            } else {
                listener(DIRECTION.NOTSCROLL)
            }
        }
    })
}


fun NestedScrollView.direction(listener: (direction: DIRECTION) -> Unit) {
    var y = 0

    this.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        val nestedScrollView = v as NestedScrollView
        if (scrollY > oldScrollY) {
            listener(DIRECTION.DOWN)
        }
        if (scrollY < oldScrollY) {
            listener(DIRECTION.UP)
        }

        if (scrollY == 0) {
        }

        if (scrollY == (nestedScrollView.measuredHeight - nestedScrollView.getChildAt(0).measuredHeight)) {
        }
    }
}