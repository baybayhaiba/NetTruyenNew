package com.example.nettruyennews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.HeaderBookBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.util.TypeBook

class AdapterHeader(
    private var headerBook: Map<TypeBook, List<Book>>,
    private val onClick: ((Book) -> Unit),
    private val longClick: ((Book) -> Boolean)
) : RecyclerView.Adapter<AdapterHeader.MyViewHolder>() {


    inner class MyViewHolder(private val binding: HeaderBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(type: TypeBook) {
            binding.apply {
                headerTitle.text = type.name
                rcvItem.adapter = AdapterBook(onClick, longClick).apply { submit(headerBook[type]) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: HeaderBookBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.header_book, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(headerBook.keys.elementAt(position))
    }

    override fun getItemCount(): Int {
        return headerBook.size
    }
}