package com.teddy.clutch.referencekotlinarch.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.teddy.clutch.referencekotlinarch.R
import com.teddy.clutch.referencekotlinarch.model.DaddyJoke
import com.teddy.clutch.referencekotlinarch.utils.view.Resource
import com.teddy.clutch.referencekotlinarch.utils.view.Status
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val viewModel by viewModel<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel.joke.observe(this, Observer {
      println("Immediately")
      it?.let { resource -> handleJoke(resource) }
    })

    viewModel.repositoryJokeResponse.observe(this, Observer {
      println("by live data")
      it?.let { resource -> handleJoke(resource) }
    })

    findViewById<Button>(R.id.now_btn).setOnClickListener {
      viewModel.getInmedeatellyRandomJoke()
    }
    findViewById<Button>(R.id.live_btn).setOnClickListener {
      viewModel.getObservableRandomJoke()
    }
  }

  private fun handleJoke(resource: Resource<DaddyJoke>) {
    when(resource.status) {
      Status.LOADING -> println("Loading")
      Status.SUCCESS -> println("Success: ${resource.data?.joke}")
      Status.ERROR -> println("Error: ${resource.message}")
    }
  }
}
