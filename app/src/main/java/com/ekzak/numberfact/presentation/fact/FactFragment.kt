package com.ekzak.numberfact.presentation.fact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ekzak.numberfact.R
import com.ekzak.numberfact.databinding.FragmentFactBinding

class FactFragment: Fragment(R.layout.fragment_fact) {

    private val binding by viewBinding(FragmentFactBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fact.text = arguments?.getString(NUMBER_KEY, "")
    }

    companion object {
        private const val NUMBER_KEY = "number"
        fun getInstance(number: String) = FactFragment().apply {
            arguments = Bundle().apply {
                putString(NUMBER_KEY, number)
            }
        }
    }
}
