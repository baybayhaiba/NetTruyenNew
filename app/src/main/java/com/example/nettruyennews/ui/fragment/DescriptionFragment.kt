package com.example.nettruyennews.ui.fragment

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.AdapterChapter
import com.example.nettruyennews.databinding.FragmentDescriptionBinding
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.Status
import com.example.nettruyennews.util.showLoading
import com.example.nettruyennews.viewmodel.DescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : BaseFragment<DescriptionViewModel, FragmentDescriptionBinding>() {
    override val layoutId: Int = R.layout.fragment_description
    override val mViewModel: DescriptionViewModel by viewModels()
    private lateinit var adapterChapter: AdapterChapter
    private val loading: AlertDialog? by lazy {
        showLoading()
    }

    override fun observerScreen() {
        // Inflate the layout for this fragment
        adapterChapter = AdapterChapter { onClick(it) }

        binding.apply {
            rcvChapter.adapter = adapterChapter
            val bundle = DescriptionFragmentArgs.fromBundle(requireArguments())
            val book = bundle.book
            mViewModel.getDescription(book).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.ERROR -> {
                        loading?.dismiss()
                    }
                    Status.LOADING -> loading?.show()
                    Status.SUCCESS -> {
                        setupData(it.data)
                    }
                }
            }

            mViewModel.chapterCurrent.observe(viewLifecycleOwner) {
                val action =
                    DescriptionFragmentDirections.actionDescriptionFragmentToDetailFragment(
                        description!!, it
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setupData(descriptionBook: DescriptionBook?) {
        if (descriptionBook == null) return

        mViewModel.descriptionCurrent = descriptionBook
        mViewModel.getBookFromDatabase()
        binding.description = descriptionBook
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        adapterChapter.submit(descriptionBook.chapter)
        loading?.dismiss()
    }

    private fun onClick(position: Int) {
        mViewModel.onClickChapter(position)
    }

    companion object {
        const val BOOK = "DATA_BOOK"
    }
}