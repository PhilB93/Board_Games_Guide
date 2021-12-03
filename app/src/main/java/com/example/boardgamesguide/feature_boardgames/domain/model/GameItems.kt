package com.example.boardgamesguide.feature_boardgames.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameItems(
    val games: List<Game>
) : Parcelable