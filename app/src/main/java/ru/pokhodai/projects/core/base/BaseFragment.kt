package ru.pokhodai.projects.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.pokhodai.projects.utils.UIError
import ru.pokhodai.projects.utils.UIState

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected open val navigationController by lazy { findNavController() }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnBackPressedDispatcher()
        setObservables()
        setListeners()
        setAdapters()
    }

    protected open fun setObservables() = Unit
    protected open fun setAdapters() = Unit
    protected open fun setListeners() = Unit

    protected open fun initOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }

    protected fun collectFlowSafely(
        lifecycleState: Lifecycle.State,
        collect: suspend () -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                collect()
            }
        }
    }

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        state: ((UIState<T>) -> Unit)? = null,
        onError: ((error: UIError) -> Unit),
        onSuccess: ((data: T) -> Unit)
    ) {
        collectFlowSafely(lifecycleState) {
            this.collect {
                state?.invoke(it)
                when (it) {
                    is UIState.Idle -> {}
                    is UIState.Loading -> {}
                    is UIState.Error -> onError.invoke(it.uiError)
                    is UIState.Success -> onSuccess.invoke(it.data)
                }
            }
        }
    }

    protected fun <T> UIState<T>.setupViewVisibility(
        view: View, loader: CircularProgressIndicator, isShowViewIfSuccess: Boolean = false
    ) {
        fun showLoader(isVisible: Boolean) {
            view.isVisible = !isVisible
            loader.isVisible = isVisible
        }

        when (this) {
            is UIState.Loading -> showLoader(true)
            is UIState.Error -> showLoader(false)
            is UIState.Success -> if (!isShowViewIfSuccess) showLoader(false)
            else -> Unit
        }
    }

    protected fun <T> UIState<T>.setupRefreshVisibility(
        swipeRefreshLayout: SwipeRefreshLayout, isShowRefreshIfSuccess: Boolean = false
    ) {

        fun showRefresh(isVisible: Boolean) {
            swipeRefreshLayout.isRefreshing = isVisible
        }

        when(this) {
            is UIState.Loading -> showRefresh(true)
            is UIState.Error -> showRefresh(false)
            is UIState.Success -> if (!isShowRefreshIfSuccess) showRefresh(false)
            else -> Unit
        }
    }

    protected open fun onBackPressed() {
        navigationController.popBackStack()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}