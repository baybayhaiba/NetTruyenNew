package com.example.nettruyennews.adapter.Paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nettruyennews.databinding.ItemBookLoaderBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    inner class LoaderViewHolder(private val binding: ItemBookLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.mlLoader.transitionToEnd()
            }
//            else {
//                binding.mlLoader.transitionToStart()
//
//                val error = loadState as LoadState.Error
//                binding.tvError.text = error.toString()
//            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookLoaderBinding.inflate(layoutInflater, parent, false)
        return LoaderViewHolder(binding)
    }

}