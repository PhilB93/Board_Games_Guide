package com.example.boardgamesguide.feature_boardgames.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentListBinding
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.presentation.adapter.BoardGameOnClickListener
import com.example.boardgamesguide.feature_boardgames.presentation.adapter.BoardGamesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), BoardGameOnClickListener {

    private val gameAdapter by lazy(LazyThreadSafetyMode.NONE) { BoardGamesAdapter(this) }
    private val binding: FragmentListBinding by viewBinding()
    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        handleEvent()
        collectData()
        fetchGames()

    }

    private fun fetchGames() = viewModel.getBoardGames()

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.recycler,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun handleEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                Log.i("123", event.toString())
                when (event) {
                    is ListViewModel.UIEvent.ShowSnackbar ->
                        showSnackbar(event.message)
                }
            }
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                binding.pbGames.isVisible = it.isLoading
                gameAdapter.submitList(it.games)
                showAdCheck(it.games)
            }
        }
    }


    private fun setupRecyclerView() = binding.recycler.apply {
        adapter = gameAdapter
    }

    private fun showAdCheck(list: List<Game>) {
        binding.ivNodata.isVisible = list.isEmpty()
    }

    override fun onClick(game: Game) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(game))
    }


}