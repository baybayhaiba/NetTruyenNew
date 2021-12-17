package com.example.nettruyennews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom
import com.example.nettruyennews.repository.DescriptionRepository
import com.example.nettruyennews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject
constructor(
    private val descriptionRepository: DescriptionRepository
) : ViewModel() {
    var descriptionCurrent: DescriptionBook? = null

    val description: DescriptionBook
        get() = descriptionCurrent!!

    val chapterCurrent = MutableLiveData<Int>()

    val isBookFavorite = MutableLiveData(false)
    val isReadedBook = MutableLiveData(false)
    private var chapterReaded: ChapterRoom? = null

    val notification = MutableLiveData<String>()

    fun getDescription(book: Book) = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        try {
            emit(Resource.success(descriptionRepository.description(book)))
        } catch (e: Exception) {
            emit(Resource.error(e.toString()))
        }
    }

    fun getBookFromDatabase() = viewModelScope.launch {
        val book =
            withContext(Dispatchers.IO) {
                descriptionRepository.getBookByLink(description.book.link)
            }.firstOrNull()
        isBookFavorite.value = book?.categories?.contains(BookRoom.FAVORITE)
        isReadedBook.value = book?.categories?.contains(BookRoom.READED)


    }


    fun isFavorite() = viewModelScope.launch {
        descriptionCurrent?.let {
            val bookByTitleDeferred = async { descriptionRepository.getBookByLink(it.book.link) }

            val bookByTitle = bookByTitleDeferred.await()

            isBookFavorite.value = bookByTitle.isNotEmpty()
        }
    }

    fun isReaded() = viewModelScope.launch {
        descriptionCurrent?.let {
            val chapterDeferred = async { descriptionRepository.getChapterByLink(it.book.link) }
            val chapter = chapterDeferred.await()
            isReadedBook.value = chapter.isNotEmpty()
            chapterReaded = chapter.firstOrNull()
        }
    }

    fun handleBookToDatabase() = viewModelScope.launch {

        if (isBookFavorite.value != null && descriptionCurrent != null) {

            val wasSave = isBookFavorite.value!!
            val description = descriptionCurrent!!


            val resultDeferred = if (wasSave) {
                async { descriptionRepository.deleteBook(description.book.title) }
            } else {
                async { descriptionRepository.saveBook(description.book).toInt() }
            }

            notificationBook(wasSave, resultDeferred.await())

            isBookFavorite.value = !wasSave
        } else {
            notification.value = "Something wrong here...."
        }
    }

    private fun notificationBook(wasSave: Boolean, result: Int) {

        var message = if (wasSave) "Delete book is " else "Save book is "
        message += if (result > 0) "Success !" else {
            "Failed"
        }

        notification.value = message
    }


    private fun saveChapter(value: Int) = viewModelScope.launch {

        val book = description.book

        val bookByLink =
            withContext(Dispatchers.IO) {
                descriptionRepository.getBookByLink(book.link)
            }.firstOrNull()

        //if dont get anybook then we create type readed
        if (bookByLink == null) {
            descriptionRepository.saveBook(BookRoom(book))
        }

        descriptionRepository.saveChapter(
            description.book.link,
            description.chapter[value]
        )
    }

    fun onClickChapter(value: Int) {
        chapterCurrent.value = value
        saveChapter(value)
    }

    fun onClickChapterFirst() {
        descriptionCurrent?.let { onClickChapter(it.chapter.size - 1) }
    }

    fun onClickChapterLast() {
        descriptionCurrent?.let { onClickChapter(0) }
    }

    fun onClickChapterReaded() {
        isReaded().invokeOnCompletion {
            chapterReaded?.let { chapterRoom ->
                onClickChapter(description.chapter.indexOfFirst { it.link == chapterRoom.link })
            }
        }
    }


}