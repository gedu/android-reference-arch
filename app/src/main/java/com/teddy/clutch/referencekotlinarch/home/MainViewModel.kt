package com.teddy.clutch.referencekotlinarch.home

import com.teddy.clutch.referencekotlinarch.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val repository: MainRepository,
    mainDispatcher: CoroutineContext) : BaseViewModel(mainDispatcher) {

  val repositoryJokeResponse = repository.listenResponse()

  fun getObservableRandomJoke() {
    this.launch(context = coroutineContext) {
      repository.getJokeLiveData()
    }
  }
}