package ru.pokhodai.projects.presentation.fragment.marvel.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.pokhodai.projects.core.base.BaseFragment
import ru.pokhodai.projects.databinding.FragmentMarvelHomeBinding

@AndroidEntryPoint
class HomeMarvelFragment :
    BaseFragment<FragmentMarvelHomeBinding>(FragmentMarvelHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}