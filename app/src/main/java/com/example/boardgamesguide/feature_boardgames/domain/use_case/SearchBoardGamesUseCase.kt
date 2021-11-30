package com.example.boardgamesguide.feature_boardgames.domain.use_case

import com.example.boardgamesguide.feature_boardgames.data.model.toGame
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchBoardGamesUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
) {
    operator fun invoke(name: String): Flow<Resource<List<Game>>>
        = flow {
        emit(Resource.Loading(emptyList()))
      //  val games = repository.searchBoardGames(name).map { it.toGame() }
        try {
            emit(
                Resource.Success(
                repository.searchBoardGames(name).map { it.toGame() }
                )
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(data = emptyList(), message = "An unexpected error occured"))
        }
        catch(e: IOException) {
            emit(Resource.Error(data = emptyList(), message = "Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)

    }