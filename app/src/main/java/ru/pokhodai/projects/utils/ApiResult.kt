package ru.pokhodai.projects.utils

import java.lang.Exception

sealed class ApiResult<out T> {
    class Success<out R>(val data: R) : ApiResult<R>()
    class Error(val error: Pair<Int, String>) : ApiResult<Nothing>()
}