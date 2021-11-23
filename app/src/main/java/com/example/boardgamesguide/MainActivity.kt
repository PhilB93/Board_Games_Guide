package com.example.boardgamesguide

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.RequestManager
import com.example.boardgamesguide.adapter.BaseGamesAdapter
import com.example.boardgamesguide.databinding.ActivityMainBinding
import com.example.boardgamesguide.ui.MainViewModel
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    @Inject
    lateinit var glide: RequestManager
    private val gameAdapter by lazy(LazyThreadSafetyMode.NONE) { BaseGamesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        fetchTopGames()
    }

    private fun setupRecyclerView() = binding.recycler.apply {
        adapter = gameAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun fetchTopGames() {
        binding.apply {
            lifecycleScope.launchWhenCreated {
                delay(500)
                mainViewModel.topGames()
                    .collectLatest {
                        when (it) {
                            is NetworkResult.DataState -> {
                                pbDog.isVisible = false
                                it.data?.let { response ->
                                    gameAdapter.submitList(response.games)
                                    Log.i("123", response.toString())
                                }
                            }
                            is NetworkResult.ErrorState -> {
                                pbDog.isVisible = false
                                //   tvText.text = "error ${it.exception}"
                            }
                            is NetworkResult.LoadingState -> {
                                pbDog.isVisible = true
                            }
                        }
                    }
            }
        }
    }
    private fun fetchRandomGame() {
        binding.apply {
            lifecycleScope.launchWhenCreated {
                delay(500)
                mainViewModel.randomGames()
                    .collectLatest {
                        when (it) {
                            is NetworkResult.DataState -> {
                                pbDog.isVisible = false
                                it.data?.let { response ->
                                    gameAdapter.submitList(response.games)
                                    Log.i("123", response.toString())
                                }
                            }
                            is NetworkResult.ErrorState -> {
                                pbDog.isVisible = false
                                //   tvText.text = "error ${it.exception}"
                            }
                            is NetworkResult.LoadingState -> {
                                pbDog.isVisible = true
                            }
                        }
                    }
            }
        }
    }
}

