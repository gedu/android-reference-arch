package com.teddy.clutch.referencekotlinarch.home

import com.teddy.clutch.referencekotlinarch.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

  val repositoryJokeResponse = repository.listenResponse()

  fun getObservableRandomJoke() {
    this.launch(context = coroutineContext) {
      repository.getJokeLiveData()
    }
  }
}