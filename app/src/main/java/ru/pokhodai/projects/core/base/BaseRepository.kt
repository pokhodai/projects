package ru.pokhodai.projects.core.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.pokhodai.projects.utils.ApiResult
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var errorHandler: ErrorHandler

    protected inline fun <T> toResultFlow(crossinline response: suspend () -> Response<T>?): Flow<ApiResult<T>> =
        flow {
            emit(ApiResult.Loading)
            runCatching {
                response()
            }.getOrNull()?.run {
                try {
                    if (isSuccessful) {
                        val body = body()
                        if (body != null) {
                            return@flow emit(ApiResult.Success(body))
                        }
                        emit(ApiResult.Error(errorHandler.errorHandle(this)))
                    } else {
                        emit(ApiResult.Error(errorHandler.errorHandle(this)))
                    }
                } catch (e: Exception) {
                   return@flow emit(ApiResult.Error(errorHandler.errorHandle(e)))
                }
            } ?: return@flow emit(ApiResult.Error(errorHandler.errorHandle()))
        }.flowOn(Dispatchers.IO)
}