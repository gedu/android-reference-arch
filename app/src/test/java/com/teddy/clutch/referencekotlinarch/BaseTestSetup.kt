package com.teddy.clutch.referencekotlinarch

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseTestSetup {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
}