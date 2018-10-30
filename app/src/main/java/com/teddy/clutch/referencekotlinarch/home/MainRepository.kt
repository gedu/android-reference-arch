package com.teddy.clutch.referencekotlinarch.home

import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource

class MainRepository(private val remoteSource: MainRemoteSource) {

  fun listenResponse() = remoteSource.result

  suspend fun getJoke() = remoteSource.getJoke()

}