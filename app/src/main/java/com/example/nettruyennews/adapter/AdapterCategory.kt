package com.example.nettruyennews.adapter

import android.util.Log
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.CategoryItemBinding
import com.example.nettruyennews.extension.AdapterRecyclerView

class AdapterCategory(val onClickCategory: ((Int) -> Unit)) :
    AdapterRecyclerView<Int, CategoryItemBinding>(
        data = mutableListOf(
            R.drawable.ic_baseline_home_24,
            R.drawable.ranking,
            R.drawable.menu,
            R.drawable.save,
            R.drawable.man
        ),
        layout = R.layout.category_item
    ), AdapterRecyclerView.Delegate<Int, CategoryItemBinding> {

    init {
        delegate = this
    }

    override fun configBinding(data: Int, binding: CategoryItemBinding) {
        binding.imgResource = data
    }

    override fun onClick(data: Int) {
        this.onClickCategory(data)
    }

    override fun onLongClick(data: Int): Boolean {
        Log.d("TAG", "onLongClick: ${data}")
        return true
    }
}