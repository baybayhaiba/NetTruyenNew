package com.example.nettruyennews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.databinding.BookItemBinding
import com.example.nettruyennews.model.Book

class AdapterBook(private val onClick: (Book) -> Unit) :
    RecyclerView.Adapter<AdapterBook.ViewHolderBook>() {

    private var books = mutableListOf<Book>()

    fun submit(books: List<Book>) {
        val diffResult = DiffUtil.calculateDiff(BookDiffUtil(this.books, books))
        this.books.clear()
        this.books.addAll(books)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolderBook(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book, onClick: (Book) -> Unit) {
            binding.apply { this.book = book }.root.setOnClickListener { onClick(book) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBook {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BookItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolderBook(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderBook, position: Int) =
        holder.bind(books[position], onClick)

    override fun getItemCount(): Int = books.size
}