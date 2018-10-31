package com.teddy.clutch.referencekotlinarch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.teddy.clutch.referencekotlinarch.R

/**
 * Basic error view to handle custom cases.
 * In the future add the possibility to pass a message and icon from xml
 *
 * Note: in the layout we should use the tag <merge> if not this way we are creating
 * 2 levels of views.
 */
class EmptyErrorView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

  init { init() }

  private fun init() {
    val contentView = LayoutInflater.from(context).inflate(R.layout.error_view, this, false)

    addView(contentView)
  }
}