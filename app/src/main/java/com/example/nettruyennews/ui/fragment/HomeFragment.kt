package com.example.nettruyennews.ui.fragment

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
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
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.Constant
import com.example.nettruyennews.util.dismissKeyboard
import com.example.nettruyennews.util.show
import com.example.nettruyennews.util.showDialog
import com.example.nettruyennews.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val mViewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_home

    private lateinit var navController: NavController

    private val pagingBook: RemoteBookPager by lazy {
        RemoteBookPager(onClick = ::onClick)
    }

    private val loaderState: LoaderStateAdapter by lazy {
        LoaderStateAdapter(::retry)
    }


    private val emptyPage = PagingData.from(emptyList<Book>())

    override fun observerScreen() {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupToolbar()

        //binding.rcvBook.adapter = adapterBook
        binding.rcvBook.adapter = pagingBook.withLoadStateFooter(loaderState)
        binding.rcvBook.isNestedScrollingEnabled = true
        binding.scrollToTop.setupWithRecyclerView(binding.rcvBook)
        binding.viewModel = mViewModel

        pagingBook.addLoadStateListener {
            val isError = it.source.refresh is LoadState.Error
            val isLoading = it.source.refresh is LoadState.Loading

            mViewModel.loading.value = isLoading

            if (isError) {
                show(it.source.refresh.toString())
            }
        }

        mViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                loadingProgress?.show()
            } else {
                loadingProgress?.dismiss()
            }
        }

        mViewModel.books.observe(viewLifecycleOwner) {
            mViewModel.viewModelScope.launch(Dispatchers.IO) {
                //refresh page
                pagingBook.submitData(emptyPage)
                //submit page
                pagingBook.submitData(it)
            }
        }

        mViewModel.error.observe(viewLifecycleOwner) {
            show(it)
        }

        mViewModel.menu.observe(viewLifecycleOwner) {
            val (link, text) = it.unzip()

            showDialog("Information", text, onClick = {
                val link = "${link[it]}?page="
                mViewModel.getBooks(link)
            })
        }

        mViewModel.ranking.observe(viewLifecycleOwner) {
            val (link, text) = it.unzip()

            showDialog("Information", text, onClick = {
                val link = "${link[it]}&page="
                mViewModel.getBooks(link)
            })
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
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val url = if (p0.isNullOrBlank()) {
                    Constant.URL_HOME
                } else {
                    "http://www.nettruyengo.com/tim-truyen?keyword=${p0}&page="
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