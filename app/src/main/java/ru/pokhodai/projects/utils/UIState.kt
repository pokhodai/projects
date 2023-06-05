package ru.pokhodai.projects.utils

sealed class UIState<out T> {
    object UILoading : UIState<Nothing>()
    class UISuccess<T>(val data: T) : UIState<T>()
    class UIError(val message: String) : UIState<Nothing>()
}