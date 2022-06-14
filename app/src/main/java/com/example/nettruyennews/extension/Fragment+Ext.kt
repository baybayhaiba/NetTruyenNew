package com.example.nettruyennews.extension

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.delay
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.nettruyennews.util.*

fun Fragment.showToast(text: String?) {
    activity?.showToast(text)
}

inline fun <reified T> Fragment.Intent(): Intent? {
    return activity?.let { it.Intent<T>() }
}


suspend fun interval(
    millisecond: Long = 1000,
    onFinish: (() -> Unit)?,
    onChangeEachLoop: () -> Boolean,
    conditionStart: Boolean?
) {
    var conditionLoop = conditionStart ?: onChangeEachLoop()
    while (!conditionLoop) {
        delay(millisecond)
        conditionLoop = onChangeEachLoop()
    }

    if (onFinish != null) {
        onFinish()
    }
}

inline fun <reified T> Fragment.startActivity(bundle: Bundle? = null) {
    activity?.let { it.startActivity<T>(bundle) }
}

fun Fragment.registerToolbar(toolbar: Toolbar?, title: String? = null) {

    Log.d("shiba", "${toolbar == null}")

    if (toolbar == null) return

    (activity as? AppCompatActivity)?.apply {
        toolbar.title = (title ?: ' ') as CharSequence?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
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


fun <T> Fragment.showDialog(
    title: String,
    data: List<T>,
    onClick: ((Int) -> Unit)?
): AlertDialog? {
    return activity?.showDialog(title, data, onClick)
}