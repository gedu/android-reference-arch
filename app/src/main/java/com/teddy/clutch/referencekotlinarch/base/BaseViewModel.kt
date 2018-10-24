package com.teddy.clutch.referencekotlinarch.base

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

  private val job = Job()

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

  override fun onCleared() {
    super.onCleared()
    job.cancel()
  }
}