package com.example.boardgamesguide.feature_boardgames.presentation.adapter

import com.example.boardgamesguide.feature_boardgames.domain.model.Game

interface BoardGameOnClickListener {
    fun onClick(game: Game)
}