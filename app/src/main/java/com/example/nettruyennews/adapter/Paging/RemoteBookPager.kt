package com.example.nettruyennews.adapter.Paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.nettruyennews.adapter.AdapterBook
import com.example.nettruyennews.databinding.BookItemBinding
import com.example.nettruyennews.model.Book

class RemoteBookPager(private val onClick: (Book) -> Unit) :
    PagingDataAdapter<Book, AdapterBook.ViewHolderBook>(REPO_COMPARATOR) {


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.title == newItem.title && oldItem.image == newItem.image
            }

            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.link == newItem.link
            }
        }
    }

    override fun onBindViewHolder(holder: AdapterBook.ViewHolderBook, position: Int) {
        holder.bind(getItem(position), onClick , longClick = null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBook.ViewHolderBook {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BookItemBinding.inflate(layoutInflater, parent, false)
        return AdapterBook.ViewHolderBook(binding)
    }

}