package com.example.nettruyennews.util

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String?) {
    activity?.showToast(text)
}

inline fun <reified T> Fragment.Intent(): Intent? {
    return activity?.let { it.Intent<T>() }
}

inline fun <reified T> Fragment.startActivity(bundle: Bundle? = null) {
    activity?.let { it.startActivity<T>(bundle) }
}

fun Fragment.show(text: String) {
    activity?.let { it.show(text) }
}

fun Fragment.showLoading(): AlertDialog? {
    return activity?.showLoading()
}

fun Fragment.dismissKeyboard() {
    activity?.dismissKeyboard()
}

fun Fragment.showDialog(
    title: String,
    message: String,
    okDialog: (() -> Unit)?,
    cancelDialog: (() -> Unit)?
) {
    activity?.showDialog(title, message, okDialog, cancelDialog)
}
