package com.teddy.clutch.referencekotlinarch

import android.app.Application
import com.teddy.clutch.referencekotlinarch.utils.di.appModule
import com.teddy.clutch.referencekotlinarch.utils.di.networkModule
import org.koin.android.ext.android.startKoin

@Suppress("unused")// it is in the Android Manifest
class ArchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(this, listOf(appModule, networkModule))
  }
}