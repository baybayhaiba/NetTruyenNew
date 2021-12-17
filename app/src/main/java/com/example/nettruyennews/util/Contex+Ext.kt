package com.example.nettruyennews.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.nettruyennews.R

fun Context.showToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

inline fun <reified T> Context.Intent(): Intent {
    return Intent(this, T::class.java)
}

inline fun <reified T> Context.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(it) }
    startActivity(intent)
}

fun Context.show(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLoading(): AlertDialog? {
    val alertBuilder = AlertDialog.Builder(this)
    alertBuilder.setCancelable(false)
    alertBuilder.setView(R.layout.layout_loading_dialog)
    return alertBuilder.create()
}

fun Activity.dismissKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isAcceptingText)
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, /*flags:*/ 0)
}

fun Context.showDialog(
    title: String,
    message: String,
    okDialog: (() -> Unit)?,
    cancelDialog: (() -> Unit)?
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            "Ok"
        ) { _, _ -> okDialog?.let { it() } }
        .setNegativeButton("Cancel") { _, _ -> cancelDialog?.let { it() } }
        .show()
}

