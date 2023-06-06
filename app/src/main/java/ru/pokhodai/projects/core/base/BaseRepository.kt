package ru.pokhodai.projects.core.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.pokhodai.projects.utils.ApiResult
import ru.pokhodai.projects.utils.DataMapper
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var errorHandler: ErrorHandler

    protected inline fun <T> toResultFlow(crossinline response: suspend () -> Response<T>?): Flow<ApiResult<T>> =
        flow {
            runCatching {
                response()
            }.getOrNull()?.run {
                try {
                    val body = body()
                    if (isSuccessful && body != null) {
                        emit(ApiResult.Success(body))
                    } else {
                        emit(ApiResult.Error(errorHandler.errorHandle(this)))
                    }
                } catch (e: Exception) {
                   emit(ApiResult.Error(Pair(this.code(), errorHandler.errorHandle(e))))
                }
            } ?: emit(ApiResult.Error(Pair(response()?.code() ?: 500, errorHandler.errorHandle())))
        }.flowOn(Dispatchers.IO)

    protected fun <ValueDto : DataMapper<Value>, Value : Any> toResultPagingFlow(
        pagingSource: BasePagingSource<ValueDto, Value>,
        pageSize: Int = 10,
        prefetchDistance: Int = pageSize,
        enablePlaceholders: Boolean = true,
        initialLoadSize: Int = pageSize * 3,
        maxSize: Int = Int.MAX_VALUE,
        jumpThreshold: Int = Int.MIN_VALUE
    ): Flow<PagingData<Value>> {
        return Pager(
            config = PagingConfig(
                pageSize,
                prefetchDistance,
                enablePlaceholders,
                initialLoadSize,
                maxSize,
                jumpThreshold
            ),
            pagingSourceFactory = {
                pagingSource
            }
        ).flow
    }
}