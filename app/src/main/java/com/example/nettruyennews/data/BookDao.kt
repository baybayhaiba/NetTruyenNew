package com.example.nettruyennews.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.nettruyennews.model.room.BookRoom

@Dao
interface BookDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveBook(book: BookRoom): Long

    @Insert(onConflict = IGNORE)
    suspend fun saveBook(books: List<BookRoom>)

    @Delete
    suspend fun deleteBook(book: BookRoom): Int

    @Update
    suspend fun updateBook(book: BookRoom): Int

    @Delete
    suspend fun deleteBook(book: List<BookRoom>): Int

    @Query("DELETE FROM table_book WHERE title = :title")
    suspend fun deleteBook(title: String): Int

    @Query("SELECT * FROM table_book WHERE link = :link")
    suspend fun getBookByLink(link: String): List<BookRoom>

    @Query("SELECT * FROM table_book ORDER BY id DESC")
    fun getBooks(): LiveData<List<BookRoom>>
}