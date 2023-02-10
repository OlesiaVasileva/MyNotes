package com.olesix.mynotes.util

sealed class Response<out T> {

    data class Success<T>(val data: T) : Response<T>()
    data class Exception(val error: kotlin.Exception) : Response<Nothing>()
}