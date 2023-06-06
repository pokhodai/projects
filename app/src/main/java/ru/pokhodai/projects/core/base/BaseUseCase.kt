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
        crossinline mappedError: (Int, String) -> Pair<Int, String> = { code, message ->
            Pair(
                code,
                message
            )
        },
        crossinline mappedData: suspend (T) -> R,
    ): Flow<ApiResult<R>> = apiResultFlow.flatMapMerge { result ->
        flow {
            when (result) {
                is ApiResult.Success -> emit(ApiResult.Success(mappedData(result.data)))
                is ApiResult.Error -> emit(
                    ApiResult.Error(
                        mappedError(
                            result.error.first,
                            result.error.second
                        )
                    )
                )
            }
        }
    }
}