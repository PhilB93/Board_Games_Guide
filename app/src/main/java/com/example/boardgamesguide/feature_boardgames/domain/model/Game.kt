package com.example.boardgamesguide.feature_boardgames.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Game(
    val average_user_rating: Double,
    val description: String,
    val description_preview: String,
    val id: String,
    val image_url: String,
    val max_players: Int,
    val max_playtime: Int,
    val min_age: Int,
    val min_players: Int,
    val min_playtime: Int,
    val name: String,
    val official_url: String,
    val price_text: String,
    val rules_url: String,
    val url: String,
    val year_published: Int,
):Parcelable