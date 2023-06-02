package ru.pokhodai.projects.utils

sealed class ApiResult<out T>(private val data: T? = null, val message: String? = null) {
    class Success<out R>(val data: R) : ApiResult<R>(data = data,)
    class Error(val exception: String) : ApiResult<Nothing>(message = exception)
    object Loading : ApiResult<Nothing>()
}