package com.teddy.clutch.referencekotlinarch.utils.di

import com.teddy.clutch.referencekotlinarch.BuildConfig
import com.teddy.clutch.referencekotlinarch.home.MainRepository
import com.teddy.clutch.referencekotlinarch.home.MainViewModel
import com.teddy.clutch.referencekotlinarch.network.DaddyJokeService
import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource
import com.teddy.clutch.referencekotlinarch.utils.api.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

  viewModel { MainViewModel(get()) }

  factory { MainRepository(get()) }

  factory { MainRemoteSource(get()) }

  single { getDaddyService(get()) }

  single { getRetrofit(get()) }

  single { getHttpClient() }
}

private fun getDaddyService(retrofit: Retrofit): DaddyJokeService {
  return retrofit.create(DaddyJokeService::class.java)
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
  return Retrofit.Builder()
      .baseUrl(BuildConfig.HOST)
      .addCallAdapterFactory(ApiResponseCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
}

private fun getHttpClient(): OkHttpClient {
  val clientBuilder = OkHttpClient.Builder ()

  clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(httpLogLevel))

  return clientBuilder.build()
}

private val httpLogLevel by lazy {
  if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}