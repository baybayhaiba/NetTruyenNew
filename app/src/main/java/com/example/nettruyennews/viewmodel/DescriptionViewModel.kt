package com.example.nettruyennews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.linkBookRoom
import com.example.nettruyennews.model.room
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom
import com.example.nettruyennews.repository.DescriptionRepository
import com.example.nettruyennews.util.file.FileUtil
import com.example.nettruyennews.util.data.Resource
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
    val notification = MutableLiveData<String>()

    private var chapterReaded: ChapterRoom? = null
    private var bookRoom: BookRoom? = null


    fun getDescription(book: Book) = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        try {
            emit(Resource.success(descriptionRepository.description(book)))
        } catch (e: Exception) {
            emit(Resource.error(e.toString()))
        }
    }

    fun getBookFromDatabase() = viewModelScope.launch {
        bookRoom =
            withContext(Dispatchers.IO) {
                descriptionRepository.getBookByLink(description.book.linkBookRoom())
            }.firstOrNull()

        isBookFavorite.value = bookRoom?.categories?.contains(BookRoom.FAVORITE)
        isReadedBook.value = bookRoom?.categories?.contains(BookRoom.READED)

    }

    fun isReaded() = viewModelScope.launch {
        descriptionCurrent?.let {
            val chapterDeferred = async { descriptionRepository.getChapterByLink(it.book.linkBookRoom()) }
            val chapter = chapterDeferred.await()
            chapterReaded = chapter.firstOrNull()
        }
    }

    private fun handleBookToDatabase(type: Int) = viewModelScope.launch {
        val resultDeferred = if (bookRoom == null) {
            async {
                FileUtil.saveImageToDevice(book = description.book.room())
                descriptionRepository.saveBook(
                    description.book.room().apply { categories.add(type) }).toInt()
            }
        } else if (bookRoom!!.categories.contains(type) && type == BookRoom.FAVORITE) {
            if (bookRoom!!.categories.size == 1) {
                async { descriptionRepository.deleteBook(bookRoom!!) }
            } else {
                async {
                    descriptionRepository.updateBook(bookRoom!!.apply {
                        categories.remove(
                            type
                        )
                    })
                }
            }
        } else {
            async { descriptionRepository.updateBook(bookRoom!!.apply { categories.add(type) }) }
        }

        if (resultDeferred.await() > 0) {
            getBookFromDatabase()
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
        handleBookToDatabase(BookRoom.READED)

        ///case need replace is example : nettruyen.com/truyen-tranh/van-co-de-nhat-te-297820
        ///when save we save like above so error link when web change domain
        ///so just save /truyen-tranh/van-co-de-nhat-te-297820


        descriptionRepository.saveChapter(
            description.book.linkBookRoom(),
            description.chapter[value]
        )

    }


    fun onClickFavorite() {
        viewModelScope.launch { handleBookToDatabase(BookRoom.FAVORITE) }
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