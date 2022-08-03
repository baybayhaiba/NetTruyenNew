package com.example.nettruyennews.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.Paging.LoaderStateAdapter
import com.example.nettruyennews.adapter.Paging.RemoteBookPager
import com.example.nettruyennews.databinding.FragmentHomeBinding
import com.example.nettruyennews.extension.*
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.*
import com.example.nettruyennews.util.Constant.TAG
import com.example.nettruyennews.util.Constant.URL_ORIGINAL
import com.example.nettruyennews.viewmodel.HomeViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val mViewModel: HomeViewModel by viewModels()
    override var mViewBinding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding
        get() = mViewBinding!!

    private lateinit var navController: NavController

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


    @SuppressLint("FragmentLiveDataObserve")
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
            setupToolbar()
            handleShimmer()


            //binding.rcvBook.adapter = adapterBook
            //binding.rcvBook.adapter = pagingBook.withLoadStateFooter(loaderState)
            binding.rcvBook.adapter = pagingBook
            binding.rcvBook.isNestedScrollingEnabled = true
            binding.scrollToTop.setupWithRecyclerView(binding.rcvBook)
            binding.viewModel = mViewModel



            lifecycleScope.launch {
                pagingBook.loadStateFlow.collectLatest {
                    val isError = it.source.refresh is LoadState.Error
                    val isLoading = it.source.refresh is LoadState.Loading
                    mViewModel.loading.value = isLoading

                    if (isError) {
                        show(it.source.refresh.toString())
                    }
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



            mViewModel.menu.observe(this) { menuMap ->
                val (link, text) = menuMap.unzip()

                showDialog("Information", text, onClick = { category ->
                    val link = "${link[category]}?page="
                    mViewModel.getBooks(link)
                })
            }

            mViewModel.ranking.observe(this) { rankMap ->
                val (link, text) = rankMap.unzip()

                showDialog("Information", text, onClick = { rank ->
                    val link = "${link[rank]}&page="
                    mViewModel.getBooks(link)
                })
            }
        }

        return mViewBinding!!.root
    }

    private fun handleShimmer() {
        val shimmer =
            binding.viewShimmer.findViewById<ShimmerFrameLayout>(R.id.view_shimmer_container)

        CoroutineScope(Dispatchers.Main).launch {
            interval(
                millisecond = 1500,
                onFinish = {
                    shimmer.hideShimmer()
                    binding.viewShimmer.isGone = true
                },
                onChangeEachLoop = { mViewModel.loading.value != true },
                conditionStart = false
            )
        }


    }

    private fun setupToolbar() {
        binding.toolbarHome.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.mnSave -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToSaveFragment()
                    findNavController().navigate(action)
                    false
                }

                R.id.mnUser -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToFlutterFragment()
                    findNavController().navigate(action)
                    false
                }

                R.id.mnRanking -> {
                    mViewModel.onClickRanking()
                    false
                }
                else -> {
                    true
                }
            }
        }

        binding.toolbarHome.setNavigationOnClickListener {
            mViewModel.onClickMenu()
        }

        val searchItem = binding.toolbarHome.menu.findItem(R.id.mnSearch)
        val searchView = searchItem.actionView as SearchView

        setupSearchView(searchView)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.isNullOrBlank() && mViewModel.URL_CURRENT != Constant.URL_HOME) {
                    Constant.URL_HOME
                    mViewModel.getBooks(Constant.URL_HOME)
                    dismissKeyboard()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val url = if (p0.isNullOrBlank()) {
                    Constant.URL_HOME
                } else {
                    "${URL_ORIGINAL}/tim-truyen?keyword=${p0}&page="
                }
                mViewModel.getBooks(url)
                dismissKeyboard()
                searchView.clearFocus()
                return true
            }
        })
    }


    private fun retry() {
        mViewModel.getBooks(mViewModel.URL_CURRENT)
    }

    private fun onClick(book: Book) {
        val action = HomeFragmentDirections.actionHomeFragmentToDescriptionFragment(book)
        findNavController().navigate(action)
    }
}