package com.ekzak.numberfact.presentation.numbers

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ekzak.numberfact.R
import com.ekzak.numberfact.databinding.FragmentNumbersBinding
import com.ekzak.numberfact.presentation.main.BaseFragment

class NumbersFragment : BaseFragment<NumbersViewModel.Base>(R.layout.fragment_numbers) {

    override var viewModelClass = NumbersViewModel.Base::class.java
    private val binding by viewBinding(FragmentNumbersBinding::bind)

    private val watcher = object : SimpleTextWatcher() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = viewModel.clearError()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                viewModel.showFact(item)
            }
        })
        binding.recycler.adapter = adapter

        viewModel.observeNumbersList(viewLifecycleOwner) {
            adapter.map(it)
        }

        viewModel.observeProgress(viewLifecycleOwner) { show ->
            binding.progress.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.observeState(viewLifecycleOwner) { state ->
            state.apply(binding.inputLayout, binding.inputNumber)
        }

        viewModel.init(savedInstanceState == null)
        handleButtonsClick()
    }

    private fun handleButtonsClick() {
        with(binding) {
            fact.setOnClickListener {
                viewModel.fetchNumberFact(inputNumber.text.toString())
            }
            randomFact.setOnClickListener {
                viewModel.fetchRandomNumberFact()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.inputNumber.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        binding.inputNumber.removeTextChangedListener(watcher)
    }

    abstract class SimpleTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) = Unit
    }
}
