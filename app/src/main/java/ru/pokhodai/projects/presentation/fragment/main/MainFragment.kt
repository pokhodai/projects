package ru.pokhodai.projects.presentation.fragment.main

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.pokhodai.projects.core.base.BaseFragment
import ru.pokhodai.projects.databinding.FragmentMainBinding
import ru.pokhodai.projects.presentation.activity.MainViewModel

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()

    override fun setListeners() = with(binding) {
        btnMarvel.setOnClickListener {
            navigationController.navigate(MainFragmentDirections.actionMainFragmentToRootMarvelFragment())
        }
    }

    override fun setObservables() = with(viewModel) {
        sdsd.collectUIState(
            state = {
                it.setupViewVisibility(binding.llMain, binding.loaderSignIn)
                it.setupRefreshVisibility(binding.srlMain)
            },
            onError = {
                it.showToast(root = binding.root)
            },
            onSuccess = {

            }
        )
    }
}