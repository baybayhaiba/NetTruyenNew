package com.example.nettruyennews.extension

fun <T> List<T>.addMore(value: T): List<T> {
    val convertMutable = this.toMutableList()
    convertMutable.add(value)
    return convertMutable.toList()
}