package com.teddy.clutch.referencekotlinarch.network

import com.teddy.clutch.referencekotlinarch.network.home.DaddyJokeResponse
import com.teddy.clutch.referencekotlinarch.utils.api.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface DaddyJokeService {

  @GET("/")
  fun getRandomJoke() : Deferred<ApiResponse<DaddyJokeResponse>>
}