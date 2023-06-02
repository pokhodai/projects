package ru.pokhodai.projects.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.pokhodai.projects.utils.ApiResult

abstract class BaseViewModel: ViewModel() {

    protected suspend inline fun <T> Flow<ApiResult<T>>.collectFlow(noinline result: suspend (data: T) -> Unit) {
        collect {
            when (it) {
                is ApiResult.Success -> {
                    return@collect result.invoke(it.data)
                }

                is ApiResult.Error -> {

                }

                is ApiResult.Loading -> {

                }
            }
        }
    }
}