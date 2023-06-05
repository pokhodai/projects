package ru.pokhodai.projects.core.base

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import ru.pokhodai.projects.utils.ApiResult

@OptIn(FlowPreview::class)
abstract class BaseUseCase {

    protected suspend inline fun <T, R> run(
        apiResultFlow: Flow<ApiResult<T>>,
        crossinline onError: (String) -> String = { message -> message },
        crossinline onSuccess: suspend (T) -> R,
    ): Flow<ApiResult<R>> = apiResultFlow.flatMapMerge { result ->
        flow {
            when (result) {
                is ApiResult.Loading -> emit(result)
                is ApiResult.Success -> emit(ApiResult.Success(onSuccess.invoke(result.data)))
                is ApiResult.Error -> emit(ApiResult.Error(onError.invoke(result.exception)))
            }
        }
    }
}