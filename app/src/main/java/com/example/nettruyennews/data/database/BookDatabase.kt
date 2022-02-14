package com.example.nettruyennews.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nettruyennews.data.remote.BookDao
import com.example.nettruyennews.data.remote.ChapterDao
import com.example.nettruyennews.data.converter.Converters
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom
import com.example.nettruyennews.util.Constant

//xem them exportSchema !!!
@Database(
    entities = [BookRoom::class, ChapterRoom::class],
    version = Constant.VERSION,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract val bookDao: BookDao
    abstract val chapterDao: ChapterDao

    companion object {
        const val DB_NAME = "book_database"

        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        DB_NAME
                    ).build()
                }

                return INSTANCE!!
            }
        }
    }
}