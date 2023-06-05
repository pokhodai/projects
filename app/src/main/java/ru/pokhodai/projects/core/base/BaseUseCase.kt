package ru.pokhodai.projects.core.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.pokhodai.projects.utils.ApiResult
import ru.pokhodai.projects.utils.UIState

abstract class BaseUseCase {

    abstract suspend operator fun invoke(): Flow<UIState<Any>>

    @OptIn(FlowPreview::class)
    suspend inline fun <T, R> run(
        flow: Flow<ApiResult<T>>,
        crossinline onLoading: () -> Unit = {},
        crossinline onError: (String) -> String = { message -> message },
        crossinline onSuccess: suspend (T) -> R,
    ): Flow<UIState<R>> {
        return flow.flatMapMerge { result ->
            flow {
                when (result) {
                    is ApiResult.Loading -> {
                        onLoading.invoke()
                        emit(UIState.UILoading)
                    }

                    is ApiResult.Success -> {
                        emit(UIState.UISuccess(onSuccess.invoke(result.data)))
                    }

                    is ApiResult.Error -> {
                        emit(UIState.UIError(onError.invoke(result.exception)))
                    }
                }
            }
        }
    }
}