package com.example.boardgamesguide.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameItems(
    val games: List<Game>
): Parcelable