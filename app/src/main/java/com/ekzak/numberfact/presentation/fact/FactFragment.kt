package com.ekzak.numberfact.presentation.fact

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ekzak.numberfact.R
import com.ekzak.numberfact.databinding.FragmentFactBinding
import com.ekzak.numberfact.presentation.main.BaseFragment

class FactFragment : BaseFragment<FactViewModel>(R.layout.fragment_fact) {

    override var viewModelClass = FactViewModel::class.java
    private val binding by viewBinding(FragmentFactBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.factDetails.text = viewModel.read()
    }
}
