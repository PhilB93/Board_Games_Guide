package com.example.boardgamesguide.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.RequestManager
import com.example.boardgamesguide.R
import com.example.boardgamesguide.adapter.BoardGameOnClickListener
import com.example.boardgamesguide.adapter.SearchGamesAdapter
import com.example.boardgamesguide.databinding.FragmentMainBinding
import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.util.NetworkResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), BoardGameOnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val gameAdapter by lazy(LazyThreadSafetyMode.NONE) { SearchGamesAdapter(this) }
    private val binding: FragmentMainBinding by viewBinding()
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUiMode()
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        handleSearchGames()
        fetchGames()
//        fetchTopGames()
//        fetchRandomGame()
//        refreshRandomGame()

    }

    private fun observeUiMode() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.darkThemeEnabled.collectLatest {
                val defaultMode = if (it) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(defaultMode)
            }
        }
    }


    private fun handleSearchGames() {
        binding.apply {

            lifecycleScope.launchWhenCreated {
                mainViewModel.searchGames.collectLatest {
                    Log.i("123", it.toString())
                    when (it) {
                        is NetworkResult.LoadingState -> {
                            showProgressBar()
                        }
                        is NetworkResult.DataState -> {
                            hideProgressBar()
                            if (it.data.games.isNotEmpty())
                                gameAdapter.submitList(it.data.games)
                            else {
                                Snackbar.make(
                                    requireContext(),
                                    binding.recycler,
                                    "No data",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }

                        }
                        is NetworkResult.ErrorState -> {
                            hideProgressBar()
                        }
                    }

                }
            }
        }
    }

    private fun fetchGames() {
        binding.svGames.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {

                if (name != null) {
                    gameAdapter.submitList(emptyList())
                    binding.recycler.scrollToPosition(0)
                    mainViewModel.searchGames(name)
                    binding.svGames.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun setupRecyclerView() = binding.recycler.apply {
        adapter = gameAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideProgressBar() {
        binding.pbGames.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbGames.visibility = View.VISIBLE
    }
//    private fun fetchTopGames() {
//        binding.apply {
//            lifecycleScope.launchWhenCreated {
//                mainViewModel.topGames()
//                    .collectLatest {
//                        when (it) {
//                            is NetworkResult.DataState -> {
//                                pbDog.isVisible = false
//
//
//                                it.data?.let { response ->
//                                    gameAdapter.submitList(response.games)
//                                }
//                            }
//                            is NetworkResult.ErrorState -> {
//                                pbDog.isVisible = false
//
//                            }
//                            is NetworkResult.LoadingState -> {
//                                pbDog.isVisible = true
//
//
//                            }
//                        }
//                    }
//            }
//        }
//    }


//    private fun fetchRandomGame() {
//        with(binding) {
//            lifecycleScope.launchWhenCreated {
//                try {
//                    mainViewModel.randomGame
//                        .collectLatest {
//                            Log.i("123", it.toString())
//                            when (it) {
//                                is NetworkResult.DataState -> {
//                                    pbDog.isVisible = false
//                                    noInternetLayout.isVisible = false
//                                    it.data?.let { response ->
//                                        setRandomGameView(response)
//                                    }
//
//                                }
//                                is NetworkResult.ErrorState -> {
//                                    pbDog.isVisible = false
//                                    //   tvText.text = "error ${it.exception}"
//                                    noInternetLayout.isVisible = true
//                                }
//                                is NetworkResult.LoadingState -> {
//                                    pbDog.isVisible = true
//                                    noInternetLayout.isVisible = false
//                                }
//                            }
//                        }
//                } catch (e: Exception) {
//                    noInternetLayout.isVisible = true
//                    e.printStackTrace()
//                    Log.i("123", "smth WRONG")
//
//                }
//            }
//        }
//    }


//    @SuppressLint("SetTextI18n")
//    private fun setRandomGameView(game: GameItems) {
//        with(binding)
//        {
//            val position = game.games[0]
//            Log.i("123", position.toString())
//            Glide.with(ivGame).load(position.image_url).apply(
////                RequestOptions()
////                    .placeholder(R.drawable.ic_search)
////                    .error(R.drawable.ic_search)
////                    .transform(RoundedCorners())
//            ).into(ivGame)
//            tvTitle.text = "${position.name} (${position.year_published})"
//            tvRating.text = String.format("%.3f", position.average_user_rating)
//            ivGame.setOnClickListener {
//                findNavController().navigate(
//                    MainFragmentDirections.actionMainFragmentToDetailsFragment(position)
//                )
//            }
//        }
//    }
//
//    private fun refreshRandomGame() {
//        binding.btnRefresh.setOnClickListener {
//            mainViewModel.randomGames()
//            Log.i("123", "Call")
//        }
//    }
//

    override fun onClick(game: Game) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(game))
    }


}