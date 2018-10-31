package com.teddy.clutch.referencekotlinarch.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.clutch.referencekotlinarch.base.BaseViewModel
import com.teddy.clutch.referencekotlinarch.model.DaddyJoke
import com.teddy.clutch.referencekotlinarch.utils.view.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

  val repositoryJokeResponse = repository.listenResponse()
  private val innerJokeResponse = MutableLiveData<Resource<DaddyJoke>>()
  val joke
    get() = innerJokeResponse as LiveData<Resource<DaddyJoke>>

  fun getObservableRandomJoke() {
    this.launch(context = coroutineContext) {
      repository.getJokeLiveData()
    }
  }

  fun getImmediatelyRandomJoke() {
    this.launch(context = coroutineContext) {
      val response = repository.getJokeImmediately()
      innerJokeResponse.postValue(response)
    }
  }
}