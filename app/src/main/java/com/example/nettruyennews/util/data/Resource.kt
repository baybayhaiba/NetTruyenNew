package com.example.nettruyennews.util.data

data class Resource<out T>(val status: Status, val data: T?, val message: String) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(Status.SUCCESS, data = data, message = "Success ")

        fun <T> loading(): Resource<T> =
            Resource(Status.LOADING, data = null, message = "Loading")

        fun <T> error(message: String): Resource<T> =
            Resource(Status.ERROR, data = null, message = message)
    }
}