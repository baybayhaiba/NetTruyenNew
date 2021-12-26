package com.example.nettruyennews.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.repository.HomeRepository
import com.example.nettruyennews.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val books = MutableLiveData<PagingData<Book>>()
    val error = MutableLiveData<String>()

    val menu = MutableLiveData<List<Pair<String, String>>>()
    val ranking = MutableLiveData<List<Pair<String, String>>>()

    val URL_CURRENT = Constant.URL

    init {
        getBooks(URL_CURRENT)
    }

    fun fetchBookPaging(url: String) = homeRepository.bookPagingFlow(url = url)

    private fun getMenu() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {
            menu.postValue(homeRepository.category())
        } catch (e: Exception) {
            error.postValue(e.message.toString())
        } finally {
            loading.postValue(false)
        }
    }

    private fun getRanking() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {
            ranking.postValue(homeRepository.ranking())
        } catch (e: Exception) {
            error.postValue(e.message.toString())
        } finally {
            loading.postValue(false)
        }
    }


    fun getBooks(url: String = Constant.URL) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newFlows = fetchBookPaging(url)
                newFlows.distinctUntilChanged().collectLatest {
                    books.postValue(it)
                }
            } catch (e: Exception) {
                error.postValue(e.message.toString())
            } finally {
                loading.postValue(false)
            }
        }


    fun onClickMenu() {
        getMenu()
    }

    fun onClickRanking() {
        getRanking()
    }
}