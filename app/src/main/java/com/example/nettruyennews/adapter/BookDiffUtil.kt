package com.example.nettruyennews.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.room.BookRoom

class BookDiffUtil(private val oldBooks: List<Book>, private val newBooks: List<Book>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldBooks.size
    }

    override fun getNewListSize(): Int {
        return newBooks.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        if (oldBooks[oldItemPosition] is BookRoom && newBooks[newItemPosition] is BookRoom) {
            val oldBook = oldBooks[oldItemPosition] as BookRoom
            val newBook = newBooks[newItemPosition] as BookRoom

            return oldBook.id == newBook.id
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBooks[oldItemPosition].image == newBooks[newItemPosition].image
                && oldBooks[oldItemPosition].link == newBooks[newItemPosition].link
                && oldBooks[oldItemPosition].title == newBooks[newItemPosition].title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}