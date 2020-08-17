package com.marvelcomics.brito

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Context?.circularProgressBar(): CircularProgressDrawable? {
    val circularProgressDrawable = this?.let { CircularProgressDrawable(it) }
    this?.let {
        circularProgressDrawable?.strokeWidth = 5f
        circularProgressDrawable?.centerRadius = 30F
        circularProgressDrawable?.backgroundColor = ContextCompat.getColor(it, R.color.colorPrimary)
        circularProgressDrawable?.start()
    }
    return circularProgressDrawable
}