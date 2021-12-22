package com.example.nettruyennews.extension

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


fun Firebase.instanceStorage(): FirebaseStorage {
    return Firebase.storage("gs://nettruyenfakes.appspot.com/")
}