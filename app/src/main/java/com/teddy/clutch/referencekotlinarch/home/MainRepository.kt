package com.teddy.clutch.referencekotlinarch.home

import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource

class MainRepository(private val remoteSource: MainRemoteSource) {

  val listenResponse get() = remoteSource.result

  suspend fun getJokeLiveData() = remoteSource.getJoke()
}