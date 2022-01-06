package com.example.nettruyennews.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


open class AdapterRecyclerView<T, VB : ViewBinding>(
    var data: MutableList<T> = mutableListOf(),
    val layout: Int,
    var delegate: Delegate<T, VB>? = null
) :
    RecyclerView.Adapter<AdapterRecyclerView<T, VB>.MyViewHolder>() {

    interface Delegate<in T, VB> {
        fun configBinding(data: T, binding: VB)
        fun onClick(data: T)
        fun onLongClick(data: T): Boolean
    }

    inner class MyViewHolder(var binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: T, delegate: Delegate<T, VB>?) {
            if (delegate != null) {
                delegate.configBinding(data, binding)
                binding.root.setOnClickListener { delegate.onClick(data) }
                binding.root.setOnLongClickListener { delegate.onLongClick(data) }
            }
        }
    }

    fun submitList(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: VB =
            DataBindingUtil.inflate(layoutInflater, layout, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.onBind(data[position], delegate)

    override fun getItemCount(): Int = data.size

}

