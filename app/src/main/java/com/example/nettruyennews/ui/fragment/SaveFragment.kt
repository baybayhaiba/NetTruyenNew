package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.adapter.AdapterBook
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.databinding.FragmentSaveBinding
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.extension.showDialog
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    private val adapter: AdapterBook by lazy {
        AdapterBook(onClick = ::onClick, longClick = ::longClick)
    }
    private val database: BookDatabase by lazy {
        BookDatabase.getInstance(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(inflater, container, false)
        binding.rcvBook.adapter = adapter
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val bookDB = BookDatabase.getInstance(requireContext())
        bookDB.bookDao.getBooks().observe(viewLifecycleOwner) {
            setupView(it)
        }

        return binding.root
    }

    private fun setupView(books: List<Book>) {
        if (books.isEmpty()) {
            binding.rcvBook.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.rcvBook.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
            adapter.submit(books)
        }
    }

    private fun longClick(book: Book): Boolean {
        showDialog(title = "Book", message = "Do you want delete book", okDialog = {
            lifecycleScope.launch(Dispatchers.IO) {

                Logger.d("Book long click $book <--> ${book is BookRoom}",)

                if (book is BookRoom) {
                    database.bookDao.deleteBook(book.title)
                }
            }
        }, cancelDialog = null)

        return true
    }


    private fun onClick(book: Book) {
//        val bundle = Bundle()
//        bundle.putParcelable(DescriptionFragment.BOOK, book)
//        startActivity<DetailActivity>(bundle)

        val action = SaveFragmentDirections.actionSaveFragmentToDescriptionFragment(book)
        findNavController().navigate(action)
    }

}