package ru.pokhodai.projects.presentation.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import ru.pokhodai.projects.core.base.BaseViewModel
import ru.pokhodai.projects.domain.use_case.marvel.CharacterUseCase
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.utils.launchDefault
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CharacterUseCase
): BaseViewModel() {

    private val _ssss = MutableUIStateFlow<List<CharacterResponse.Data.Result>>()
    val sdsd = _ssss.asStateFlow()

    init {
        launchDefault {
            useCase().collectRequest(_ssss)
        }
    }


}