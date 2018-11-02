package com.teddy.clutch.referencekotlinarch.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.teddy.clutch.referencekotlinarch.network.DaddyJokeService
import com.teddy.clutch.referencekotlinarch.network.home.DaddyJokeResponse
import com.teddy.clutch.referencekotlinarch.network.home.MainRemoteSource
import com.teddy.clutch.referencekotlinarch.utils.api.ApiResponse
import com.teddy.clutch.referencekotlinarch.utils.view.Status
import kotlinx.coroutines.*
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi //For the use of Dispatchers.Unconfined)
class MainViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test
  fun getSuccessJoke() {
    val repository = MainRepository(MainRemoteSource(DaddyJokeServiceTest("Testing")))
    val viewModel = MainViewModel(repository, Dispatchers.Unconfined)

    runBlocking { viewModel.getObservableRandomJoke() }

    val resource = viewModel.repositoryJokeResponse.value

    Truth.assertThat(resource!!.status).isEqualTo(Status.SUCCESS)
    Truth.assertThat(resource.data!!.joke).isEqualTo("Testing")
  }

  @Test
  fun getErrorJoke() {
    val repository = MainRepository(MainRemoteSource(DaddyJokeServiceTest("Testing", true)))
    val viewModel = MainViewModel(repository, Dispatchers.Unconfined)

    runBlocking { viewModel.getObservableRandomJoke() }

    val resource = viewModel.repositoryJokeResponse.value

    Truth.assertThat(resource!!.status).isEqualTo(Status.ERROR)
    Truth.assertThat(resource.message).isEqualTo("error")
  }
}

private class DaddyJokeServiceTest(
    private val joke: String,
    private val error: Boolean = false
) : DaddyJokeService {

  override fun getRandomJoke(): Deferred<ApiResponse<DaddyJokeResponse>> {
    val daddyJokeResponse = DaddyJokeResponse("-1", joke, if (error) 500 else 200)
    val deferred = CompletableDeferred<ApiResponse<DaddyJokeResponse>>()

    runBlocking {
      if (error) {
        deferred.complete(ApiResponse.create(Throwable("error")))
      } else {
        deferred.complete(ApiResponse.create(Response.success(daddyJokeResponse)))
      }
    }

    return deferred
  }
}