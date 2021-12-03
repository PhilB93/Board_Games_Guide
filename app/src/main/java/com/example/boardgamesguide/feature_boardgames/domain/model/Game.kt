package com.example.boardgamesguide.feature_boardgames.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val average_user_rating: Double,
    val id: String,
    val image_url: String,
    val name: String,
    val url: String,
    val year_published: Int
) : Parcelable
