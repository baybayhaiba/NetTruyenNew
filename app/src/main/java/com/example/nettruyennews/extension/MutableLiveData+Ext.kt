package com.example.nettruyennews.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<T>.plus(anotherLiveData: MutableLiveData<T>): List<MutableLiveData<T>> {
    val combineLiveData = mutableListOf<MutableLiveData<T>>()
    combineLiveData.add(this)
    combineLiveData.add(anotherLiveData)

    return combineLiveData
}

fun <T> List<MutableLiveData<T>>.observer(lifecycler: LifecycleOwner, onClick: ((T) -> Unit)) {

    for (liveData in this) {
        liveData.observe(lifecycler) { onClick(it) }
    }
}