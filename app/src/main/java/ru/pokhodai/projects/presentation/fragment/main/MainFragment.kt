package ru.pokhodai.projects.presentation.fragment.main

import dagger.hilt.android.AndroidEntryPoint
import ru.pokhodai.projects.core.base.BaseFragment
import ru.pokhodai.projects.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun setListeners() = with(binding) {
        btnMarvel.setOnClickListener {
            navigationController.navigate(MainFragmentDirections.actionMainFragmentToRootMarvelFragment())
        }
    }
}