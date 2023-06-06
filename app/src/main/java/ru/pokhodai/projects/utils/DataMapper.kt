package ru.pokhodai.projects.utils

interface DataMapper<T> {
    fun mapToDomain(): T
}