package com.example.boardgamesguide.feature_boardgames.domain.use_case

import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.InputException
import com.example.boardgamesguide.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchBoardGamesUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
) {
    operator fun invoke(name: String): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            val games = repository.searchBoardGames(name)
            if (games.isEmpty())
                throw InputException()
            else
                emit(
                    Resource.Success(
                        games
                    )
                )
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    data = emptyList(),
                    message = "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    data = emptyList(),
                    message = "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: InputException) {
            emit(
                Resource.Error(
                    data = emptyList(),
                    message = "Plz check your input"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

}