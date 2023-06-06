package ru.pokhodai.projects.utils

import android.view.View
import android.widget.Toast

sealed class UIState<T> {
    class Idle<T> : UIState<T>()
    class Loading<T> : UIState<T>()
    class Error<T>(val uiError: UIError) : UIState<T>()
    class Success<T>(val data: T) : UIState<T>()
}

class UIError(private val code: Int, private val message: String) {

    val uiCode: Int
        get() = code

    val uiMessage: String
        get() = message

    fun showToast(root: View) {
        Toast.makeText(root.context, uiMessage, Toast.LENGTH_LONG).show()
    }
}