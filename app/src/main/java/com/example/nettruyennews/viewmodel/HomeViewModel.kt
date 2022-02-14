package com.example.nettruyennews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.repository.home.HomeRepository
import com.example.nettruyennews.repository.home.HomeRepositoryImpl
import com.example.nettruyennews.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepositoryImpl: HomeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val books = MutableLiveData<PagingData<Book>>()
    val error = MutableLiveData<String>()

    val menu = MutableLiveData<List<Pair<String, String>>>()
    val ranking = MutableLiveData<List<Pair<String, String>>>()

    var URL_CURRENT: String = ""

    init {
        getBooks()
    }


    fun fetchBookPaging(url: String) = homeRepositoryImpl.pageFlow(url = url)

    private fun getMenu() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {
            menu.postValue(homeRepositoryImpl.category())
        } catch (e: Exception) {
            error.postValue(e.message.toString())
        } finally {
            loading.postValue(false)
        }
    }

    private fun getRanking() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {
            ranking.postValue(homeRepositoryImpl.ranking())
        } catch (e: Exception) {
            error.postValue(e.message.toString())
        } finally {
            loading.postValue(false)
        }
    }


    fun getBooks(url: String = Constant.URL_HOME) =
        viewModelScope.launch(Dispatchers.IO) {
            URL_CURRENT = url
            val newFlows = fetchBookPaging(url)
            newFlows.distinctUntilChanged().collectLatest {
                books.postValue(it)
            }
        }


    fun onClickMenu() {
        getMenu()
    }

    fun onClickRanking() {
        getRanking()
    }
}