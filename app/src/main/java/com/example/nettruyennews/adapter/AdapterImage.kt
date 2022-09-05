package com.example.nettruyennews.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.databinding.ImageItemBinding
import com.example.nettruyennews.databinding.ItemNavigationChapterBinding
import com.example.nettruyennews.viewmodel.DetailViewModel

class AdapterImage(private val onClick: (String) -> Unit, private val viewModel: DetailViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val images = mutableListOf<String>()

    fun submit(images: List<String>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    inner class ViewHolderImage(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(image: String, onClick: (String) -> Unit) {
            binding.image = image
            binding.root.setOnClickListener { onClick(image) }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderImage)
            holder.onBind(onClick = onClick, image = images[position])
    }


    override fun getItemCount(): Int = images.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolderImage(ImageItemBinding.inflate(inflater, parent, false))
    }

}


