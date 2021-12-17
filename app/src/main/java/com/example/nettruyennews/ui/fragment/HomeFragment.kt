package com.example.nettruyennews.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nettruyennews.R
import com.example.nettruyennews.adapter.AdapterBook
import com.example.nettruyennews.databinding.FragmentHomeBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.ui.DetailActivity
import com.example.nettruyennews.ui.base.BaseFragment
import com.example.nettruyennews.util.*
import com.example.nettruyennews.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val mViewModel: HomeViewModel by viewModels()
    override var mViewBinding: FragmentHomeBinding? = null

    val binding: FragmentHomeBinding
        get() = mViewBinding!!

    private lateinit var navController: NavController
    private lateinit var adapter: AdapterBook
    private val loading: AlertDialog? by lazy {
        showLoading()
    }


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
            adapter = AdapterBook { onClick(it) }
            binding.rcvBook.adapter = adapter
            binding.rcvBook.isNestedScrollingEnabled = false
            binding.viewModel = mViewModel
            binding.swipeRefreshLayout.setOnRefreshListener { mViewModel.getBooks() }

            mViewModel.loading.observe(this) {
                if (it) {
                    loading?.show()
                } else {
                    dismiss()
                }
            }

            mViewModel.books.observe(this) {
                getBooks(it)
            }

            mViewModel.error.observe(this) {
                show(it)
            }

            (mViewModel.menu + mViewModel.ranking).observer(this) { data ->
                val (link, text) = data.unzip()

                showDialog("Information", text, onClick = {
                    mViewModel.getBooks(link[it])
                })
            }
        }


        return mViewBinding!!.root
    }

    private fun dismiss() {
        loading?.dismiss()
        mViewBinding?.swipeRefreshLayout?.isRefreshing = false
    }

    private fun getBooks(books: List<Book>) {
        adapter.submit(books)
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
                    "http://www.nettruyengo.com/tim-truyen?keyword=${p0}"
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


    private fun onClick(book: Book) {
        val bundle = Bundle()
        bundle.putParcelable(DescriptionFragment.BOOK, book)
        startActivity<DetailActivity>(bundle)
//        val action = HomeFragmentDirections.actionHomeFragmentToDescriptionFragment(book)
//        findNavController().navigate(action)
    }
}