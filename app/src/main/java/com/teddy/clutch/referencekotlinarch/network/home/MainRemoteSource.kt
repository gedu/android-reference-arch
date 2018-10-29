package com.teddy.clutch.referencekotlinarch.network.home

import com.teddy.clutch.referencekotlinarch.network.DaddyJokeService
import com.teddy.clutch.referencekotlinarch.utils.api.ApiEmptyResponse
import com.teddy.clutch.referencekotlinarch.utils.api.ApiErrorResponse
import com.teddy.clutch.referencekotlinarch.utils.api.ApiSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteSource(val daddyJokeService: DaddyJokeService) {

  suspend fun getJoke() {
    withContext(Dispatchers.IO) {
      val response = daddyJokeService.getRandomJoke().await()
      when (response) {
        is ApiSuccessResponse -> {

        }
        is ApiEmptyResponse -> {

        }
        is ApiErrorResponse -> {

        }
      }
    }
  }
}