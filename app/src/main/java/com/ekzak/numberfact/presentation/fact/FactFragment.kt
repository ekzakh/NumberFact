package com.ekzak.numberfact.presentation.fact

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ekzak.numberfact.R

class FactFragment: Fragment(R.layout.fragment_fact) {

    companion object {
        private const val NUMBER_KEY = "number"
        fun getInstance(number: String) = FactFragment().apply {
            arguments = Bundle().apply {
                putString(NUMBER_KEY, number)
            }
        }
    }
}
