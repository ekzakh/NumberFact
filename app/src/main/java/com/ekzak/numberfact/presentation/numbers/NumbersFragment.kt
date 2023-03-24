package com.ekzak.numberfact.presentation.numbers

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ekzak.numberfact.R
import com.ekzak.numberfact.databinding.FragmentNumbersBinding
import com.ekzak.numberfact.presentation.MainActivity
import com.ekzak.numberfact.presentation.fact.FactFragment

class NumbersFragment : Fragment(R.layout.fragment_numbers) {

    private val binding by viewBinding(FragmentNumbersBinding::bind)
    private lateinit var showFragment: ShowFragment
    private lateinit var viewModel: NumbersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            showFragment = context
        } else {
            throw IllegalAccessException("NumbersListener must implement")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                showFragment.show(FactFragment.getInstance(item.ui()))
            }
        })
        binding.recycler.adapter = adapter

        binding.inputNumber.addTextChangedListener(object : SimpleTextWatcher() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                super.beforeTextChanged(s, start, count, after)
                viewModel.clearError()
            }
        })

        viewModel.observeNumbersList(viewLifecycleOwner) {
            adapter.map(it)
        }

        viewModel.observeProgress(viewLifecycleOwner) { show ->
            binding.progress.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.observeState(viewLifecycleOwner) { state ->
            state.apply(binding.inputLayout, binding.inputNumber)
        }

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

    companion object {
        fun getInstance() = NumbersFragment()
    }

    abstract class SimpleTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) = Unit
    }
}
