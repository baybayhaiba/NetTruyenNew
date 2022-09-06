package com.example.nettruyennews.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.ItemNavigationChapterBinding


enum class ChapterBehavior {
    previous, next
}

interface ChapterListener {
    fun onClick(behavior: ChapterBehavior)
}




class ItemNavigationChapter : ConstraintLayout {

    val binding: ItemNavigationChapterBinding
    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    private lateinit var chapterListener: ChapterListener

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        binding = ItemNavigationChapterBinding.inflate(inflater, this, true)
        //val attribute = context.obtainStyledAttributes(attrs, R.styleable.ChapterNavigation)


        binding.apply {
            buttonPrevious.setOnClickListener { chapterListener.onClick(ChapterBehavior.previous) }
            buttonNext.setOnClickListener { chapterListener.onClick(ChapterBehavior.next) }
        }
    }

    fun setContent(content : String){
        binding.tvTitleAndChapter.text = content
    }

    fun registerListener(chapterListener: ChapterListener) {
        this.chapterListener = chapterListener
    }
}

