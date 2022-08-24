package com.example.nettruyennews.viewmodel

import androidx.lifecycle.*
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.repository.DetailRepository
import com.example.nettruyennews.util.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) :
    ViewModel() {

    var currentIndex = MutableLiveData<Int>()
    var descriptionBook: DescriptionBook? = null

    val description: DescriptionBook
        get() = descriptionBook!!
    private val index: Int
        get() = currentIndex.value!!


    val chapterCurrent = Transformations.map(currentIndex) {

        if (currentIndex.value != null && descriptionBook != null) {
            saveChapter()

            return@map descriptionBook?.chapter?.get(index)?.title.toString()
        }

        return@map "Unknown chapter"
    }


    fun getImages(currentIndex: Int, descriptionBook: DescriptionBook) = liveData(Dispatchers.IO) {

        emit(Resource.loading())

        try {
            emit(Resource.success(detailRepository.detail(descriptionBook, currentIndex)))
        } catch (e: Exception) {
            emit(Resource.error(e.toString()))
        }
    }


    private fun saveChapter() = viewModelScope.launch {

        detailRepository.saveChapter(
            description.book.link,
            description.chapter[index]
        )
    }

    fun onClickNextChapter() {
        currentIndex.value?.let {
            if (it == 0) return

            currentIndex.value = it - 1
        }
    }

    fun onClickPreviousChapter() {
        currentIndex.value?.let {
            if (it + 1 == descriptionBook?.chapter?.size) return

            currentIndex.value = it + 1
        }
    }
}