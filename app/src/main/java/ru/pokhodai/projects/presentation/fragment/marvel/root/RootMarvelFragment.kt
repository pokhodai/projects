package ru.pokhodai.projects.presentation.fragment.marvel.root

import android.os.Bundle
import android.view.View
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.pokhodai.projects.R
import ru.pokhodai.projects.core.base.BaseFragment
import ru.pokhodai.projects.databinding.FragmentRootMarvelBinding

@AndroidEntryPoint
class RootMarvelFragment :
    BaseFragment<FragmentRootMarvelBinding>(FragmentRootMarvelBinding::inflate) {

    override fun initOnBackPressedDispatcher() = Unit

    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.fcvRootMarvel) as NavHostFragment
    }

    override val navigationController by lazy {
        navHostFragment.navController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bnvRootMarvel.setupWithNavController(navigationController)
    }

    override fun setAdapters() = with(binding) {
        bnvRootMarvel.setOnItemReselectedListener {
            val option = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                navigationController.graph.findStartDestination().id,
                inclusive = false,
                saveState = false
            ).build()

            runCatching {
                navigationController.navigate(it.itemId, null, option)
            }
        }
    }
}