package ru.pokhodai.projects.core.base

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.pokhodai.projects.utils.ApiResult
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    inline fun <T> toResultFlow(crossinline response: suspend () -> Response<T>?): Flow<ApiResult<T>> {
        return flow {
            emit(ApiResult.Loading)
            response()?.let { call ->
                try {
                    if (call.isSuccessful) {
                        val body = call.body()
                        if (body != null) {
                            emit(ApiResult.Success(body))
                            return@flow
                        }
                        emit(ApiResult.Error(errorHandle(call)))
                    } else {
                        emit(ApiResult.Error(errorHandle(call)))
                    }
                } catch (e: Exception) {
                    emit(ApiResult.Error(errorHandle(e)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun <T> errorHandle(call: Response<T>): String {
        return if (call.isSuccessful) {
            call.message()
        } else {
            runCatching {
                org.json.JSONObject(
                    call.errorBody()?.string().toString()
                )[ERROR_MESSAGE_KEY].toString()
            }.getOrNull() ?: call.message()
        }
    }

    fun errorHandle(e: Exception): String {
        return when (e) {
            is SocketTimeoutException, is ConnectException -> RequestError.CONNECT.message
            is CancellationException -> RequestError.COROUTINE_CANCEL.message
            else -> RequestError.NONE.message
        }
    }

    enum class RequestError(val message: String) {
        CONNECT("error connect"),
        COROUTINE_CANCEL("coroutine cancel"),
        NONE("error")
    }

    companion object {
        const val ERROR_MESSAGE_KEY = "message"
    }
}