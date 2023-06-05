package ru.pokhodai.projects.core.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.pokhodai.projects.utils.ApiResult
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var errorHandle: ErrorHandle

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
                        emit(ApiResult.Error(errorHandle.errorHandle(this)))
                    } else {
                        emit(ApiResult.Error(errorHandle.errorHandle(this)))
                    }
                } catch (e: Exception) {
                   return@flow emit(ApiResult.Error(errorHandle.errorHandle(e)))
                }
            } ?: return@flow emit(ApiResult.Error(errorHandle.errorHandle()))
        }.flowOn(Dispatchers.IO)
}