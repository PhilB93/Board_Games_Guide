package com.example.boardgamesguide.domain.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.network.ApiService
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val LIMIT = 10
private const val ORDER = "rank"

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    val context: Context

) : BoardGamesInfoRepository {
    override fun topBoardGames(): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            emit(
                NetworkResult.DataState(
                    apiService.topBoardGames(
                        limit = LIMIT,
                        order_by = ORDER
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e, "smthng wrong"))
        }
    }.flowOn(Dispatchers.IO)

    override fun randomBoardGameInfo(
    ): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {

            emit(
                NetworkResult.DataState(
                    apiService.randomBoardGames(
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e, "smthng wrong"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getBoardGameById(id: String): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {

            emit(NetworkResult.DataState(apiService.getGameById(id = id)))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e, "smthng wrong"))


        }
    }

    override fun searchBoardGames(name: String): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            if (hasInternetConnection()) {
            delay(1000)
                emit(
                    NetworkResult.DataState(
                        apiService.searchBoardGames(
                            name = name
                        )
                    )
                )
            } else emit(
                NetworkResult.ErrorState(
                    exception = null,
                    message = "No internet connection"
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(exception = e, message = "smthng wrong"))
        }
    }.flowOn(Dispatchers.IO)


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication(context).getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}