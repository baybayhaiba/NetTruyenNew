package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nettruyennews.adapter.AdapterBook
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.databinding.FragmentSaveBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.ui.DetailActivity
import com.example.nettruyennews.util.startActivity

class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    private val adapter: AdapterBook by lazy {
        AdapterBook { onClick(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(inflater, container, false)
        binding.rcvBook.adapter = adapter

        val bookDB = BookDatabase.getInstance(requireContext())
        bookDB.bookDao.getBooks().observe(this) {
            adapter.submit(it)
        }

        return binding.root
    }

    private fun onClick(book: Book) {
        val bundle = Bundle()
        bundle.putParcelable(DescriptionFragment.BOOK, book)
        startActivity<DetailActivity>(bundle)
    }

}