package com.teddy.clutch.referencekotlinarch.network.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.clutch.referencekotlinarch.model.DaddyJoke
import com.teddy.clutch.referencekotlinarch.network.DaddyJokeService
import com.teddy.clutch.referencekotlinarch.utils.api.ApiEmptyResponse
import com.teddy.clutch.referencekotlinarch.utils.api.ApiErrorResponse
import com.teddy.clutch.referencekotlinarch.utils.api.ApiSuccessResponse
import com.teddy.clutch.referencekotlinarch.utils.view.Resource
import kotlinx.coroutines.coroutineScope

class MainRemoteSource(private val daddyJokeService: DaddyJokeService) {

  private val _result = MutableLiveData<Resource<DaddyJoke>>()
  val result
    get() = _result as LiveData<Resource<DaddyJoke>>

  suspend fun getJoke() = coroutineScope {
    _result.value = Resource.loading(null)
    val response = daddyJokeService.getRandomJoke().await()
    when (response) {
      is ApiSuccessResponse -> {
        val daddyJoke = DaddyJoke(response.body.id, response.body.joke)
        _result.postValue(Resource.success(daddyJoke))
      }
      is ApiEmptyResponse -> {
        _result.postValue(Resource.error("No joke", null))
      }
      is ApiErrorResponse -> {
        _result.postValue(Resource.error(response.errorMessage, null))
      }
    }
  }
}