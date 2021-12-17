package com.example.nettruyennews.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.nettruyennews.model.room.ChapterRoom

@Dao
interface ChapterDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveChapter(chapterRoom: ChapterRoom): Long

    @Update
    suspend fun updateChapter(chapterRoom: ChapterRoom)

    @Query(
        "SELECT table_chapter.* FROM table_chapter JOIN table_book ON table_chapter.root = table_book.link WHERE table_chapter.root LIKE :link"
    )
    suspend fun chapterByLink(link: String): List<ChapterRoom>

    @Query("SELECT * FROM table_chapter")
    suspend fun chapters(): List<ChapterRoom>
}