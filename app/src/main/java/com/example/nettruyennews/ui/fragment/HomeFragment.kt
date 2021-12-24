package com.example.nettruyennews.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.AdapterCategory
import com.example.nettruyennews.adapter.Paging.LoaderStateAdapter
import com.example.nettruyennews.adapter.Paging.RemoteBookPager
import com.example.nettruyennews.databinding.FragmentHomeBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.*
import com.example.nettruyennews.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val mViewModel: HomeViewModel by viewModels()
    override var mViewBinding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding
        get() = mViewBinding!!

    private lateinit var navController: NavController

    private val adapterCategories: AdapterCategory by lazy {
        AdapterCategory(onClickCategory = ::onClickCategories)
    }
    private val pagingBook: RemoteBookPager by lazy {
        RemoteBookPager(onClick = ::onClick)
    }

    private val loaderState: LoaderStateAdapter by lazy {
        LoaderStateAdapter(::retry)
    }

    private val loading: AlertDialog? by lazy {
        showLoading()
    }

    private val emptyPage = PagingData.from(emptyList<Book>())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mViewBinding == null) {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.containerFragment) as NavHostFragment
            navController = navHostFragment.navController
            mViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
            setupSearchView()

            binding.rcvCategories.adapter = adapterCategories
            binding.rcvCategories.isNestedScrollingEnabled = false
            //binding.rcvBook.adapter = adapterBook
            binding.rcvBook.adapter = pagingBook.withLoadStateFooter(loaderState)
            binding.rcvBook.isNestedScrollingEnabled = true
            binding.viewModel = mViewModel

            mViewModel.loading.observe(this) {
                if (it) {
                    loading?.show()
                } else {
                    dismiss()
                }
            }

            mViewModel.books.observe(this) {
                mViewModel.viewModelScope.launch(Dispatchers.IO) {
                    //refresh page
                    pagingBook.submitData(emptyPage)
                    //submit page
                    pagingBook.submitData(it)
                }
            }

            mViewModel.error.observe(this) {
                show(it)
            }

            mViewModel.menu.observe(this) {
                val (link, text) = it.unzip()

                showDialog("Information", text, onClick = {
                    val link = "${link[it]}?page="
                    mViewModel.getBooks(link)
                })
            }

            mViewModel.ranking.observe(this) {
                val (link, text) = it.unzip()

                showDialog("Information", text, onClick = {
                    val link = "${link[it]}&page="
                    mViewModel.getBooks(link)
                })
            }
        }


        return mViewBinding!!.root
    }


    private fun dismiss() {
        loading?.dismiss()
    }

    private fun setupSearchView() {
        binding.searchViewBook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val url = if (p0.isNullOrBlank()) {
                    Constant.URL
                } else {
                    "http://www.nettruyengo.com/tim-truyen?keyword=${p0}&page="
                }
                mViewModel.getBooks(url)
                clearSearchView()
                return true
            }
        })
    }

    private fun clearSearchView() {
        dismissKeyboard()
        binding.searchViewBook.clearFocus()
        binding.searchViewBook.setQuery("", false)
    }

    private fun retry() {
        show("retry book !")
    }

    private fun onClick(book: Book) {
        val action = HomeFragmentDirections.actionHomeFragmentToDescriptionFragment(book)
        findNavController().navigate(action)
    }

    private fun onClickCategories(imageResource: Int) {
        when (imageResource) {
            R.drawable.ranking -> mViewModel.onClickRanking()
            R.drawable.menu -> mViewModel.onClickMenu()
            R.drawable.ic_baseline_home_24 -> mViewModel.getBooks(mViewModel.URL_CURRENT)
            R.drawable.save -> {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSaveFragment()
                findNavController().navigate(action)
            }

            R.drawable.man -> {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToUserFragment()
                findNavController().navigate(action)
            }
        }
    }
}
