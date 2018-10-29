package com.teddy.clutch.referencekotlinarch.home

import com.teddy.clutch.referencekotlinarch.model.DaddyJoke
import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource
import com.teddy.clutch.referencekotlinarch.utils.view.Resource

class MainRepository(val remoteSource: MainRemoteSource) {

  fun getJoke(): Resource<DaddyJoke> {
    return Resource.error("Not done", null)
  }
}