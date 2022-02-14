package com.example.nettruyennews.ui.fragment

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.AdapterImage
import com.example.nettruyennews.databinding.FragmentDetailBinding
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.Status
import com.example.nettruyennews.util.showToast
import com.example.nettruyennews.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {
    override val layoutId: Int = R.layout.fragment_detail
    override val mViewModel: DetailViewModel by viewModels()
    private lateinit var adapterImage: AdapterImage


    override fun observerScreen() {
        adapterImage = AdapterImage { onClickImage(it) }

        this.binding.rcvImage.adapter = adapterImage
        this.binding.viewModel = mViewModel
        this.binding.lifecycleOwner = this

        DetailFragmentArgs.fromBundle(requireArguments()).let {
            val description = it.book
            val chapterCurrent = it.chapterCurrent

            mViewModel.apply {
                this.descriptionBook = description
                this.currentIndex.value = chapterCurrent

                currentIndex.observe(this@DetailFragment) {
                    reloadChapter()
                    mViewModel.getImages(it, description)
                        .observe(this@DetailFragment) { resource ->
                            when (resource.status) {
                                Status.LOADING -> showToast(resource.message)
                                Status.ERROR -> showToast(resource.message)
                                Status.SUCCESS -> {
                                    setupData(resource.data)
                                }
                            }
                        }
                }
            }
        }
    }

    private fun reloadChapter() {
        this.binding.layoutButton.visibility = View.GONE
        adapterImage.submit(emptyList())
        //clearCache()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        clearCache()
//    }
//
//    private fun clearCache() = runBlocking {
//        withContext(Dispatchers.IO) {
//            activity?.applicationContext?.let { Glide.get(it).clearDiskCache() }
//        }
//    }


    private fun setupData(images: List<String>?) {
        if (images == null) return
        adapterImage.submit(images)
        this.binding.layoutButton.isVisible = true
    }

    private fun onClickImage(image: String) {
        showToast(image)
    }
}