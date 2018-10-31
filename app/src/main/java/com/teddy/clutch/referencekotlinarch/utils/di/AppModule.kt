package com.teddy.clutch.referencekotlinarch.utils.di

import com.teddy.clutch.referencekotlinarch.home.MainRepository
import com.teddy.clutch.referencekotlinarch.home.MainViewModel
import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

  viewModel { MainViewModel(get()) }

  factory { MainRepository(get()) }

  factory { MainRemoteSource(get()) }
}