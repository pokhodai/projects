package ru.pokhodai.projects.core.base

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.pokhodai.projects.utils.ApiResult
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

abstract class BaseRepository {

    protected inline fun <T> toResultFlow(crossinline response: suspend () -> Response<T>?): Flow<ApiResult<T>> {
        return flow {
            emit(ApiResult.Loading)
            runCatching {
                response()
            }.getOrNull()?.let { call ->
                try {
                    if (call.isSuccessful) {
                        val body = call.body()
                        if (body != null) {
                            return@flow emit(ApiResult.Success(body))
                        }
                        emit(ApiResult.Error(errorHandle(call)))
                    } else {
                        emit(ApiResult.Error(errorHandle(call)))
                    }
                } catch (e: Exception) {
                    return@flow emit(ApiResult.Error(errorHandle(e)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    protected fun <T> errorHandle(call: Response<T>): String {
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

    protected fun errorHandle(e: Exception): String {
        return when (e) {
            is SocketTimeoutException, is ConnectException -> RequestError.CONNECT.message
            is CancellationException -> RequestError.COROUTINE_CANCEL.message
            is UnknownHostException -> RequestError.UNKNOWN_HOST.message
            else -> RequestError.NONE.message
        }
    }

    protected enum class RequestError(val message: String) {
        CONNECT("error connect"),
        COROUTINE_CANCEL("coroutine cancel"),
        UNKNOWN_HOST("unknown"),
        NONE("error")
    }

    companion object {
        const val ERROR_MESSAGE_KEY = "message"
    }
}