package ru.pokhodai.projects.utils

sealed class ApiResult<out T> {
    class Success<out R>(val data: R) : ApiResult<R>()
    class Error(val exception: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}