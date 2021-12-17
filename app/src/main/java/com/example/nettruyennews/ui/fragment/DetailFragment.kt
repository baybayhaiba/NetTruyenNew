package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.nettruyennews.adapter.AdapterImage
import com.example.nettruyennews.databinding.FragmentDetailBinding
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.*
import com.example.nettruyennews.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    override val mViewModel: DetailViewModel by viewModels()
    override var mViewBinding: FragmentDetailBinding? = null
    private val binding get() = mViewBinding!!
    private lateinit var adapterImage: AdapterImage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (mViewBinding == null) {
            mViewBinding = FragmentDetailBinding.inflate(inflater, container, false)
            adapterImage = AdapterImage { onClickImage(it) }

            binding.rcvImage.adapter = adapterImage
            binding.viewModel = mViewModel
            binding.lifecycleOwner = this


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

        return binding.root
    }


    private fun reloadChapter() {
        binding.layoutButton.visibility = View.GONE
    }

    private fun setupData(images: List<String>?) {
        if (images == null) return
        adapterImage.submit(images)
        binding.layoutButton.isVisible = true
    }

    private fun onClickImage(image: String) {
        showToast(image)
    }
}