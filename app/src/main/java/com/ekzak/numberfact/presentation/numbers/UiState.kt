package com.ekzak.numberfact.presentation.numbers

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class UiState {

    abstract fun apply(inputLayout: TextInputLayout, inputText: TextInputEditText)

    object Success : UiState() {

        override fun apply(inputLayout: TextInputLayout, inputText: TextInputEditText) {
            inputText.setText("")
        }
    }

    abstract class AbstractError(private val message: String, private val errorEnabled: Boolean) : UiState() {

        override fun apply(inputLayout: TextInputLayout, inputText: TextInputEditText) = with(inputLayout) {
            isErrorEnabled = errorEnabled
            error = message
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)

    object ClearError : AbstractError("", false)
}
