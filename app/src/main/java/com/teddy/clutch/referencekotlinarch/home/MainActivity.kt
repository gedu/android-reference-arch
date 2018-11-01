package com.teddy.clutch.referencekotlinarch.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.teddy.clutch.referencekotlinarch.R
import com.teddy.clutch.referencekotlinarch.model.DaddyJoke
import com.teddy.clutch.referencekotlinarch.utils.extensions.hide
import com.teddy.clutch.referencekotlinarch.utils.extensions.show
import com.teddy.clutch.referencekotlinarch.utils.view.Resource
import com.teddy.clutch.referencekotlinarch.utils.view.Status
import com.teddy.clutch.referencekotlinarch.utils.view.bindView
import com.teddy.clutch.referencekotlinarch.widget.EmptyErrorView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val viewModel by viewModel<MainViewModel>()

  private val jokeText by bindView<TextView>(R.id.joke)
  private val loadingView by bindView<ProgressBar>(R.id.progress_bar)
  private val errorView by bindView<EmptyErrorView>(R.id.error_message)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupViewModels()
    setupListeners()
  }

  private fun setupViewModels() {
    viewModel.repositoryJokeResponse.observe(this, Observer {
      it?.let { resource -> handleJoke(resource) }
    })
  }

  private fun setupListeners() {
    findViewById<Button>(R.id.now_btn).setOnClickListener {
      viewModel.getObservableRandomJoke()
    }
  }

  private fun handleJoke(resource: Resource<DaddyJoke>) {
    when(resource.status) {
      Status.LOADING -> showLoading()
      Status.SUCCESS -> resource.data?.joke?.let { showJoke(it) }
      Status.ERROR -> showError()
    }
  }

  private fun showLoading() {
    errorView.hide()
    loadingView.show()
    jokeText.hide()
  }

  private fun showJoke(joke: String) {
    errorView.hide()
    loadingView.hide()
    jokeText.show()
    jokeText.text = joke
  }

  private fun showError() {
    loadingView.hide()
    jokeText.hide()
    errorView.show()
  }
}
