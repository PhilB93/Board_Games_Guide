package com.example.boardgamesguide.feature_boardgames.data.model

import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems


data class GameItemsDto(
    val games: List<GameDto>
)
fun GameItemsDto.toGameItems(): GameItems = GameItems(games.map { it.toGame() })
