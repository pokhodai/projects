package ru.pokhodai.projects.core.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun <T> errorHandle(call: Response<T>): Pair<Int, String> {
        return Pair(
            call.code(), if (call.isSuccessful) {
                call.message()
            } else {
                runCatching {
                    JSONObject(
                        call.errorBody()?.string().toString()
                    )[ERROR_MESSAGE_KEY].toString()

                }.getOrNull() ?: call.message()
            }
        )
    }

    fun errorHandle(e: Exception): String {
        return when (e) {
            is SocketTimeoutException, is ConnectException -> RequestError.CONNECT.message
            is CancellationException -> RequestError.COROUTINE_CANCEL.message
            is UnknownHostException -> RequestError.UNKNOWN_HOST.message
            is TimeoutException -> RequestError.TIMEOUT.message
            else -> errorHandle(e)
        }
    }

    fun errorHandle(): String {
        val cm = context.getSystemService(ConnectivityManager::class.java)
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (nc != null) {
            val wifi = nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val cellular = nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            when {
                wifi && cellular -> RequestError.INTERNET.message
                wifi -> RequestError.WIFI.message
                cellular -> RequestError.CELLULAR.message
                else -> RequestError.NONE.message
            }
        } else {
            RequestError.INTERNET.message
        }
    }

    enum class RequestError(val message: String) {
        CONNECT("error connect"),
        COROUTINE_CANCEL("coroutine cancel"),
        UNKNOWN_HOST("unknown"),
        TIMEOUT("Error time out"),
        WIFI("Error wifi"),
        CELLULAR("Error cellular"),
        INTERNET("Error internet"),
        NONE("error")
    }

    companion object {
        private const val ERROR_MESSAGE_KEY = "message"
    }
}