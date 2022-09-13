package com.example.nettruyennews.extension

import com.example.nettruyennews.util.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun Firebase.instanceStorage(): FirebaseStorage {
    return Firebase.storage("gs://nettruyenfakes.appspot.com/")
}

fun Firebase.instanceRealtime() =
    Firebase.database("https://nettruyenfakes-default-rtdb.asia-southeast1.firebasedatabase.app/")

suspend fun Firebase.url() = suspendCoroutine<String?> { callback ->
    Firebase.instanceRealtime().getReference("url_nettruyen")
        .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) =
                callback.resume(snapshot.value as? String)

            override fun onCancelled(error: DatabaseError) = callback.resume(null)

        })

}

fun Firebase.getUrlNettruyen(result: (String?) -> Unit) =
    Firebase.instanceRealtime().getReference("url_nettruyen")
        .addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) = result(snapshot.getValue<String>())

            override fun onCancelled(error: DatabaseError) = throw Exception("Url null")

        })