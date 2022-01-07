package com.example.nettruyennews.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    override val mViewModel: DescriptionViewModel by viewModels()
    override var mViewBinding: FragmentDescriptionBinding? = null
    private lateinit var adapterChapter: AdapterChapter
    private val loading: AlertDialog? by lazy {
        showLoading()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (mViewBinding == null) {
            mViewBinding = FragmentDescriptionBinding.inflate(inflater, container, false)
            adapterChapter = AdapterChapter { onClick(it) }

            mViewBinding?.apply {
                rcvChapter.adapter = adapterChapter
                val bundle = DescriptionFragmentArgs.fromBundle(arguments!!)
                val book = bundle.book
                mViewModel.getDescription(book).observe(this@DescriptionFragment) {
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

                mViewModel.chapterCurrent.observe(this@DescriptionFragment) {
                    val action =
                        DescriptionFragmentDirections.actionDescriptionFragmentToDetailFragment(
                            description!!, it
                        )
                    findNavController().navigate(action)
                }
            }
        }


        return mViewBinding!!.root
    }


    private fun setupData(descriptionBook: DescriptionBook?) {
        if (descriptionBook == null) return

        mViewModel.descriptionCurrent = descriptionBook
        mViewModel.getBookFromDatabase()
        mViewBinding?.description = descriptionBook
        mViewBinding?.lifecycleOwner = this
        mViewBinding?.viewModel = mViewModel
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