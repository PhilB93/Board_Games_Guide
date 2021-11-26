package com.example.boardgamesguide.util

sealed class NetworkResult<out T> {
    object LoadingState : NetworkResult<Nothing>()
    data class ErrorState(var exception: Throwable?, var message:String) : NetworkResult<Nothing>()
    data class DataState<T>(var data: T) : NetworkResult<T>()
}