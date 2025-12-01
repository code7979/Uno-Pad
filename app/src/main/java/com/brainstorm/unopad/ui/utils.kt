package com.brainstorm.unopad.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun placeCursorAtEnd(context: Context, editText: EditText) {
    editText.requestFocus()
    editText.setSelection(editText.text.length) // place cursor at end
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun hideSoftKeyboard(context: Context, editText: EditText) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}