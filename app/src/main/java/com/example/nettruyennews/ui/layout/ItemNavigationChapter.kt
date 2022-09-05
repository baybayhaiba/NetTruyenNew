package com.example.nettruyennews.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.ItemNavigationChapterBinding


enum class ChapterBehavior {
    previous, next
}

interface ChapterListener {
    fun onClick(behavior: ChapterBehavior)
}

class ItemNavigationChapter(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs, R.layout.item_navigation_chapter) {


    private lateinit var chapterListener: ChapterListener

    fun registerListener(chapterListener: ChapterListener) {
        this.chapterListener = chapterListener
    }


    init {

        val attribute = context?.obtainStyledAttributes(attrs, R.styleable.ChapterNavigation)
        val view = inflate(context, R.layout.item_navigation_chapter, this)
        val binding = ItemNavigationChapterBinding.bind(view)

        try {
            binding.apply {
                buttonPrevious.setOnClickListener { chapterListener.onClick(ChapterBehavior.previous) }
                buttonNext.setOnClickListener { chapterListener.onClick(ChapterBehavior.next) }
                tvTitleAndChapter.text =
                    "${attribute?.getString(R.styleable.ChapterNavigation_title)}\n${
                        attribute?.getString(R.styleable.ChapterNavigation_chapter)
                    }"
            }
        } finally {
            attribute?.recycle()
        }


    }
}