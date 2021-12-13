package com.example.boardgamesguide.feature_boardgames.presentation.search

import com.example.boardgamesguide.feature_boardgames.domain.model.Game

data class GameListState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList(),
    val error: String = ""
)