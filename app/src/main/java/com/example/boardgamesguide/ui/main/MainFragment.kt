package com.example.boardgamesguide.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.adapter.BaseGamesAdapter
import com.example.boardgamesguide.adapter.BoardGameOnClickListener
import com.example.boardgamesguide.databinding.FragmentMainBinding
import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.util.Constants.Companion.ROUNDED_CORNERS
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), BoardGameOnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val gameAdapter by lazy(LazyThreadSafetyMode.NONE) { BaseGamesAdapter(this) }
    private val binding: FragmentMainBinding by viewBinding()

    private val mainViewModel by viewModels<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchTopGames()
        fetchRandomGame()
        refreshRandomGame()

    }


    private fun setupRecyclerView() = binding.recycler.apply {
        adapter = gameAdapter
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
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
                                setVisibilityForUI(true)
                                it.data?.let { response ->
                                    gameAdapter.submitList(response.games)
                                }
                            }
                            is NetworkResult.ErrorState -> {
                                pbDog.isVisible = false
                            }
                            is NetworkResult.LoadingState -> {
                                pbDog.isVisible = true
                                setVisibilityForUI(false)
                            }
                        }
                    }
            }
        }
    }

    private fun setVisibilityForUI(visible: Boolean) {
        with(binding)
        {
            tvTopgames.isVisible = visible
            tvRandomGame.isVisible = visible
            randomGame.isVisible = visible
            btnRefresh.isVisible = visible
        }
    }

    private fun fetchRandomGame() {
       with(binding) {
            lifecycleScope.launchWhenCreated {
                try {
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
                catch (e:Exception)
                {
                    e.printStackTrace()
                    Log.i("123", "smth WRONG")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setRandomGameView(game: GameItems) {
        with(binding)
        {
            val position = game.games[0]
            Log.i("123", position.toString())
            Glide.with(ivGame).load(position.image_url).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_search)
                    .error(R.drawable.ic_search)
                    .transform(RoundedCorners(ROUNDED_CORNERS))
            ).into(ivGame)
            tvTitle.text = "${position.name} (${position.year_published})"
            tvRating.text = String.format("%.3f", position.average_user_rating)
            ivGame.setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(position)
                )
            }
        }
    }

    private fun refreshRandomGame() {
        binding.btnRefresh.setOnClickListener {
            mainViewModel.randomGames()
            Log.i("123", "Call")
        }
    }


    override fun onClick(game: Game) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(game))
    }


}