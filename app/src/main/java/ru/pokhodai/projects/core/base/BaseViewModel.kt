package ru.pokhodai.projects.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.pokhodai.projects.utils.ApiResult
import ru.pokhodai.projects.utils.UIError
import ru.pokhodai.projects.utils.UIState

abstract class BaseViewModel: ViewModel() {

    @Suppress("FunctionName")
    protected fun <T> MutableUIStateFlow() = MutableStateFlow<UIState<T>>(UIState.Idle())

    fun <T> MutableStateFlow<UIState<T>>.reset() {
        value = UIState.Idle()
    }

    protected fun <T, R> Flow<ApiResult<T>>.collectRequest(
        state: MutableStateFlow<UIState<R>>,
        mappedData: (T) -> R
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@collectRequest.collect {
                when (it) {
                    is ApiResult.Error -> state.value = UIState.Error(UIError(it.error.first, it.error.second))
                    is ApiResult.Success -> state.value = UIState.Success(mappedData(it.data))
                }
            }
        }
    }

    protected fun <T> Flow<ApiResult<T>>.collectRequest(
        state: MutableStateFlow<UIState<T>>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@collectRequest.collect {
                when (it) {
                    is ApiResult.Error -> state.value = UIState.Error(UIError(it.error.first, it.error.second))
                    is ApiResult.Success -> state.value = UIState.Success(it.data)
                }
            }
        }
    }
}