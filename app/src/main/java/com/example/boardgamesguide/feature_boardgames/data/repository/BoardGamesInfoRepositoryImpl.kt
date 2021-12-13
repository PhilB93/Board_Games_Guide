package com.example.boardgamesguide.feature_boardgames.data.repository

import com.example.boardgamesguide.feature_boardgames.data.local.BoardGamesDao
import com.example.boardgamesguide.feature_boardgames.data.remote.ApiService
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.InputException
import com.example.boardgamesguide.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: BoardGamesDao
) : BoardGamesInfoRepository {

    override  fun searchBoardGames(name: String): Flow<Resource<List<Game>>> = flow {

        emit(Resource.Loading(emptyList()))
        val games = dao.getBoardGames(name).map { it.toGame() }
        emit(Resource.Loading(games))
        try {
            val remoteBoardGames = apiService.searchBoardGames(name).games
            dao.deleteBoardGames(remoteBoardGames.map { it.name })
            dao.insertBoardGames(remoteBoardGames.map { it.toBoardGamesEntity() })
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    data = games,
                    message = "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    data = games,
                    message = "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: InputException) {
            emit(
                Resource.Error(
                    data =games,
                    message = "Plz check your input"
                )
            )
        }
        val newBoardGames = dao.getBoardGames(name).map { it.toGame() }
        emit(Resource.Success(newBoardGames))
    }
}
