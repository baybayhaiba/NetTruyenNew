package com.example.myfile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.PhotoItemBinding
import com.example.nettruyennews.extension.toUriExtension
import com.example.nettruyennews.util.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class PhotoType {
    CAMERA, GALLARY, NORMAL
}

class PhotoAdapter(private val context: Context?, private val onClick: (Photo) -> Unit) :
    RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    private var photos = mutableListOf<Photo>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {

                photos.add(Photo("", 0, false, PhotoType.CAMERA))
                photos.add(Photo("", 1, false, PhotoType.GALLARY))

                val bonusPhoto = 2

                for ((index, image) in FileUtil.getImages(it).withIndex()) {
                    photos.add(Photo(image, index + bonusPhoto))
                }

                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }

            }
        }
    }

    inner class MyViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, onClick: (Photo) -> Unit) {


            when (photo.type) {
                PhotoType.NORMAL -> {
                    Glide.with(binding.root)
                        .load(photo.image.toUriExtension())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(binding.imgPhoto)
                }

                PhotoType.CAMERA, PhotoType.GALLARY -> {
                    val image = if (photo.type == PhotoType.CAMERA) {
                        R.drawable.ic_baseline_camera_alt_24
                    } else {
                        R.drawable.gallary
                    }
                    Glide.with(binding.root)
                        .load(image)
                        .into(binding.imgPhoto)
                }
            }

            binding.imgPhoto.alpha = if (photo.isSelected) {
                0.2F
            } else {
                1F
            }

            binding.root.setOnClickListener { onClick(photo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhotoItemBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(photos[position], onClick)

    override fun getItemCount(): Int = photos.size
}

class Photo(
    val image: String,
    val index: Int = -1,
    var isSelected: Boolean = false,
    val type: PhotoType = PhotoType.NORMAL
)