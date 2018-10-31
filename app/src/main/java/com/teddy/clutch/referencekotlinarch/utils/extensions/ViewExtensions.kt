package com.teddy.clutch.referencekotlinarch.utils.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.hide() {
  this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes resourceId: Int): View =
    LayoutInflater.from(context).inflate(resourceId, this, false)