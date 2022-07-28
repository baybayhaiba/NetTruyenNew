package com.example.nettruyennews.extension

import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

enum class DIRECTION_VERTICAL {
    UP, DOWN, NOTSCROLL
}

fun RecyclerView.isLastItem(newItems: ((Boolean) -> Unit)) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                newItems(true)
            }
        }
    })
}


fun RecyclerView.listenScroll(
    isShow: ((Boolean) -> Unit)?,
) {

    var isFirst: Boolean = true

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

//            when {
//                dx > 0 -> {
//                    //System.out.println("Scrolled Right");
//                }
//                dx < 0 -> {
//                    //System.out.println("Scrolled Left");
//                }
//                else -> {
//                    //System.out.println("No Horizontal Scrolled");
//                }
//            }

            when {
//                dy > 0 -> {
//                    //listener(DIRECTION_VERTICAL.DOWN)
//                    //System.out.println("Scrolled Downwards");
//                }
//                dy < 0 -> {
//                    //listener(DIRECTION_VERTICAL.UP)
//                    isShow?.invoke(true)
//                    //System.out.println("Scrolled Upwards");
//                }
                else -> {
                    isShow?.invoke(false)
                    //listener(DIRECTION_VERTICAL.NOTSCROLL)
                    //System.out.println("No Vertical Scrolled");
                }
            }


            if (!recyclerView.canScrollVertically(1)) {
                isShow?.invoke(true)
            } else {
                isFirst = false
            }
        }
    })
}

fun RecyclerView.direction(listener: (directionVERTICAL: DIRECTION_VERTICAL) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            Log.d("scroll view", "onScrolled: ${dy}")

            if (dy > 0) {
                listener(DIRECTION_VERTICAL.UP)
            } else if (dy < 0) {
                listener(DIRECTION_VERTICAL.DOWN)
            } else {
                listener(DIRECTION_VERTICAL.NOTSCROLL)
            }
        }
    })
}


fun NestedScrollView.direction(listener: (directionVERTICAL: DIRECTION_VERTICAL) -> Unit) {
    var y = 0

    this.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        val nestedScrollView = v as NestedScrollView
        if (scrollY > oldScrollY) {
            listener(DIRECTION_VERTICAL.DOWN)
        }
        if (scrollY < oldScrollY) {
            listener(DIRECTION_VERTICAL.UP)
        }

        if (scrollY == 0) {
        }

        if (scrollY == (nestedScrollView.measuredHeight - nestedScrollView.getChildAt(0).measuredHeight)) {
        }
    }
}