package com.ekzak.numberfact.presentation.numbers

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ekzak.numberfact.R
import com.ekzak.numberfact.databinding.FragmentNumbersBinding
import com.ekzak.numberfact.presentation.MainActivity

class NumbersFragment: Fragment(R.layout.fragment_numbers) {

    private val binding by viewBinding(FragmentNumbersBinding::bind)
    private lateinit var listener: NumbersListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            listener = context
        } else {
            throw IllegalAccessException("NumbersListener must implement")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.randomFact.setOnClickListener {
            listener.factClicked()
        }
    }

    companion object {
        fun getInstance() = NumbersFragment()
    }
}

