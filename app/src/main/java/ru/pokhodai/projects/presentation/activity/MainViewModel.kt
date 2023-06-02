package ru.pokhodai.projects.presentation.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.pokhodai.projects.core.base.BaseRepository
import ru.pokhodai.projects.core.base.BaseViewModel
import ru.pokhodai.projects.data.repository.MarvelRepository
import ru.pokhodai.projects.utils.ApiResult
import ru.pokhodai.projects.utils.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
): BaseViewModel() {

    init {
        launch {
            marvelRepository.getCharacters().collect() {
                if (it is ApiResult.Error) {
                    Log.d("TAGATG", "Tag "+it.exception)
                }
            }
        }
    }


}