package ru.pokhodai.projects.core.base

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import ru.pokhodai.projects.utils.ApiResult

abstract class UseCase {

    @OptIn(FlowPreview::class)
    protected suspend inline fun <T, R> run(
        flow: Flow<ApiResult<T>>,
        crossinline onError: (String) -> String = { message -> message },
        crossinline onSuccess: suspend (T) -> R,
    ): Flow<ApiResult<R>> {

        return flow.flatMapMerge { result ->
            flow {
                when (result) {
                    is ApiResult.Loading -> emit(result)
                    is ApiResult.Success -> emit(ApiResult.Success(onSuccess.invoke(result.data)))
                    is ApiResult.Error -> emit(ApiResult.Error(onError.invoke(result.exception)))
                }
            }
        }
    }
}