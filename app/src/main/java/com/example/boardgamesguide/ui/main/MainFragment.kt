package com.example.boardgamesguide.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.boardgamesguide.R
import com.example.boardgamesguide.adapter.BaseGamesAdapter
import com.example.boardgamesguide.databinding.FragmentMainBinding
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val mainViewModel by viewModels<MainViewModel>()
    private val binding: FragmentMainBinding by viewBinding()
    @Inject
    lateinit var glide: RequestManager
    private val gameAdapter by lazy(LazyThreadSafetyMode.NONE) { BaseGamesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchTopGames()
        fetchRandomGame()
        refreshRandomGame()
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
                mainViewModel.randomGame
                    .collectLatest {
                        when (it) {
                            is NetworkResult.DataState -> {
                                pbDog.isVisible = false
                                it.data?.let { response ->
                                    setRandomGameView(response)
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

    private fun setRandomGameView(game: GameItems) {
        with(binding)
        {
            val position = game.games[(game.games.indices).random()]
            Log.i("123", position.toString())
            Glide.with(ivGame).load(position.image_url).into(ivGame)
            tvTitle.text = position.name
            tvRating.text = String.format("%.3f", position.average_user_rating)

        }
    }
    private fun refreshRandomGame(){
        binding.btnRefresh.setOnClickListener {
            mainViewModel.randomGames()
            Log.i("123", "Call")
        }
    }

}