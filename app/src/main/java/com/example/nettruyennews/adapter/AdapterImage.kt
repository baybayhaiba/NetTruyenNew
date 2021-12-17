package com.example.nettruyennews.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.databinding.ImageItemBinding

class AdapterImage(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<AdapterImage.ViewHolderImage>() {

    private val images = mutableListOf<String>()

    fun submit(images: List<String>) {
        this.images.clear()
        this.images.addAll(images)
        this.notifyDataSetChanged()
    }

    inner class ViewHolderImage(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(image: String, onClick: (String) -> Unit) {
            binding.image = image

            Log.d(TAG, "onBind: ${image}")
            binding.root.setOnClickListener { onClick(image) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImage {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)
        return ViewHolderImage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderImage, position: Int) =
        holder.onBind(images[position], onClick)

    override fun getItemCount(): Int = images.size
}