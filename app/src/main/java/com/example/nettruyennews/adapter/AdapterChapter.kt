package com.example.nettruyennews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.databinding.ChapterItemBinding
import com.example.nettruyennews.model.Chapter

class AdapterChapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<AdapterChapter.ViewHolderChapter>() {

    private val chapters = mutableListOf<Chapter>()

    fun submit(chapters: List<Chapter>) {
        this.chapters.clear()
        this.chapters.addAll(chapters)
        this.notifyDataSetChanged()
    }

    inner class ViewHolderChapter(private val binding: ChapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int, onClick: (Int) -> Unit) {
            binding.title = chapters[position].title
            binding.root.setOnClickListener { onClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChapterItemBinding.inflate(inflater, parent, false)
        return ViewHolderChapter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderChapter, position: Int) =
        holder.onBind(position, onClick)

    override fun getItemCount(): Int = chapters.size
}