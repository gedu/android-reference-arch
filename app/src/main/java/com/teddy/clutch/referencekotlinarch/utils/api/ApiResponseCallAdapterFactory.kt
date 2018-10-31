package com.teddy.clutch.referencekotlinarch.utils.api

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.Executor

class ApiResponseCallAdapterFactory private constructor() : CallAdapter.Factory() {
  companion object {
    @JvmStatic @JvmName("create")
    operator fun invoke() = ApiResponseCallAdapterFactory()
  }

  override fun get(
      returnType: Type,
      annotations: Array<out Annotation>,
      retrofit: Retrofit
  ): CallAdapter<*, *>? {

    if (getRawType(returnType) != Deferred::class.java) {
      return null
    }

    val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
    val rawObservableType = CallAdapter.Factory.getRawType(observableType)
    if (rawObservableType != ApiResponse::class.java) {
      throw IllegalArgumentException("type must be a resource")
    }
    if (observableType !is ParameterizedType) {
      throw IllegalArgumentException("resource must be parameterized")
    }

    val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
    return ApiCall2Adapter<Any>(bodyType, retrofit.callbackExecutor())
  }
}

/**
 * A Retrofit adapter that converts the Call into a Deferred of ApiResponse.
 * @param <R>
</R> */
class ApiCall2Adapter<R>(
    private val responseType: Type,
    private val executor: Executor?
) : CallAdapter<R, Deferred<ApiResponse<R>>> {

  override fun responseType() = responseType

  override fun adapt(call: Call<R>): Deferred<ApiResponse<R>> {
    val deferred = CompletableDeferred<ApiResponse<R>>()

    deferred.invokeOnCompletion {
      if (deferred.isCancelled) {
        call.cancel()
      }
    }

    call.enqueue(object : Callback<R> {
      override fun onFailure(call: Call<R>, t: Throwable) {
        deferred.complete(ApiResponse.create(t))
      }

      override fun onResponse(call: Call<R>, response: Response<R>) {
        if (response.isSuccessful) {
          deferred.complete(ApiResponse.create(response))
        } else {
          deferred.complete(ApiResponse.create(HttpException(response)))
        }
      }
    })

    return deferred
  }
}