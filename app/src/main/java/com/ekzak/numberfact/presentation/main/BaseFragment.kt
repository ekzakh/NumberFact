package com.ekzak.numberfact.presentation.main

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.ekzak.numberfact.sl.main.ProvideViewModel

abstract class BaseFragment<T : ViewModel>(@LayoutRes id: Int) : Fragment(id) {

    lateinit var viewModel: T
    abstract var viewModelClass: Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(viewModelClass, this)
    }
}
