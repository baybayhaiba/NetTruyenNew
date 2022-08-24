package com.example.nettruyennews.util.data.persistance

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.nettruyennews.model.room.BookRoom
import com.google.gson.Gson

object NettruyenPersistance {

    private val core = "NettruyenPersistance"
    private val lastestReaded: String = "lastestReaded"
    private var preferences: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreferences {

        synchronized(this) {
            if (preferences == null) {
                preferences = context.getSharedPreferences(core, MODE_PRIVATE)
            }
            return preferences!!
        }
    }

    fun saveBookCurrent(bookRoom: BookRoom) {
        preferences?.edit {
            putString(lastestReaded, Gson().toJson(bookRoom))
        }
    }

    fun getBookLastest(json: String?): BookRoom? = try {
        Gson().fromJson(json, BookRoom::class.java)
    } catch (e: Exception) {
        null
    }
}