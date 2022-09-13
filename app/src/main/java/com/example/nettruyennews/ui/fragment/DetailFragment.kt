package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nettruyennews.adapter.AdapterImage
import com.example.nettruyennews.databinding.FragmentDetailBinding
import com.example.nettruyennews.extension.DIRECTION_VERTICAL
import com.example.nettruyennews.extension.listenScroll
import com.example.nettruyennews.extension.showToast
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.ui.layout.ChapterBehavior
import com.example.nettruyennews.ui.layout.ChapterListener
import com.example.nettruyennews.util.data.Status
import com.example.nettruyennews.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(), ChapterListener {

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

            adapterImage = AdapterImage(
                onClick = {
                    onClickImage(it)
                }, viewModel = mViewModel
            )

            registerRecyclerView()
            binding.layoutNavigation.registerListener(this)
            binding.viewModel = mViewModel

            DetailFragmentArgs.fromBundle(requireArguments()).let {
                val description = it.book
                val chapterCurrent = it.chapterCurrent


                mViewModel.apply {
                    //just observer for register listen from layout
                    this.chapterCurrent.observe(viewLifecycleOwner) {}
                    this.descriptionBook = description
                    this.currentIndex.value = chapterCurrent

                    currentIndex.observe(viewLifecycleOwner) { chapter ->
                        reloadChapter()
                        mViewModel.getImages(chapter, description)
                            .observe(viewLifecycleOwner) { resource ->
                                when (resource.status) {
                                    Status.LOADING -> showToast(resource.message)
                                    Status.ERROR -> showToast(resource.message)
                                    Status.SUCCESS -> setupData(resource.data)
                                }
                            }
                    }
                }
            }
        }

        return binding.root
    }


    private fun registerRecyclerView() {
        binding.rcvImage.adapter = adapterImage
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        binding.rcvImage.listenScroll(scroll = {
            if (it == DIRECTION_VERTICAL.UP) {
                mViewModel.navigation.value = false
            }
        }, end = {
            mViewModel.navigation.value = true
        })
    }


    private fun reloadChapter() {
        adapterImage.submit(emptyList())
        clearCache()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }

    private fun clearCache() = runBlocking {
        withContext(Dispatchers.IO) {
            activity?.applicationContext?.let { Glide.get(it).clearDiskCache() }
        }
    }


    private fun setupData(images: List<String>?) {
        if (images == null) return
        adapterImage.submit(images)
    }

    private fun onClickImage(image: String) = showToast(image)
    override fun onClick(behavior: ChapterBehavior) =
        if (behavior == ChapterBehavior.next) mViewModel.onClickNextChapter() else mViewModel.onClickPreviousChapter()
}