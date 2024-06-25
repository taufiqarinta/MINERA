package com.minera.minera.utils

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun EditText.isEmpty() = this.text.toString().isEmpty()

fun Fragment.toast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Activity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun validateEditText(editTextList: List<EditText>): Boolean {
    for (editText in editTextList) {
        if (editText.text.isEmpty()) {
            editText.requestFocus()
            editText.error = "Kolom ini wajib diisi"
            return true
        }
    }
    return false
}