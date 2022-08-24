package com.example.nettruyennews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.Paging.RemoteBookPager
import com.example.nettruyennews.databinding.HeaderBookBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.util.data.TypeBook

class AdapterHeader(
    private val context: Context?,
    private val onClick: ((Book) -> Unit)
) : RecyclerView.Adapter<AdapterHeader.MyViewHolder>() {

    val pageBook = hashMapOf(
        TypeBook.HOT to RemoteBookPager(onClick), TypeBook.HOME to RemoteBookPager(onClick)
    )

    inner class MyViewHolder(private val binding: HeaderBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(type: TypeBook) {

            binding.apply {
                headerTitle.text = type.name
                val bookPage = pageBook[type]

                if (type == TypeBook.HOT) {
                    rcvItem.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                } else {
                    rcvItem.layoutManager = GridLayoutManager(context, 2)
                }


                rcvItem.adapter = bookPage
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
        holder.onBind(pageBook.keys.elementAt(position))
    }

    override fun getItemCount(): Int {
        return pageBook.size
    }
}