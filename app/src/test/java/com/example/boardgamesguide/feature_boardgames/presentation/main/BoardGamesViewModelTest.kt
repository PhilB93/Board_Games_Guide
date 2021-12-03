package com.example.boardgamesguide.feature_boardgames.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.prefsstore.PrefsStore
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.util.MainCoroutineRule
import com.example.boardgamesguide.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class BoardGamesViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @MockK
    lateinit var searchBoardGamesUseCase: SearchBoardGamesUseCase

    @MockK
    lateinit var prefsStore: PrefsStore

    @MockK
    lateinit var mainViewModel: BoardGamesViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test() = runBlockingTest {
        val listTestMockK = listOf<Game>(
            Game(
                0.0,
                "", "", "", "", 0
            )
        )

        val listOfResource = listOf(
            Resource.Loading(),
            Resource.Success(listTestMockK)
        )

        val testString = "batman"
        val list = listOf(false, true)
        //  val listExpTest = listOf(Resource.Loading, Resource.Success)
        // every { prefsStore.isNightMode() } returns flowOf(true, false)
        every { prefsStore.isNightMode() } returns flowOf(*list.toTypedArray())
        every {
            searchBoardGamesUseCase(testString)
        } returns flowOf(*listOfResource.toTypedArray())

        mainViewModel = BoardGamesViewModel(searchBoardGamesUseCase, prefsStore)

//        mainViewModel.darkThemeEnabled.collectIndexed { i, el ->
//            Assert.assertEquals(list[i], el)
//        }

        val expectedResults = listOf<GameListState>(
            GameListState(),
            GameListState(
                isLoading = true
            ),
            GameListState(
                isLoading = false,
                games = listTestMockK
            ),
        )
        launch {
            mainViewModel.state.collectIndexed { i, el ->
                Assert.assertEquals(expectedResults[i], el)

                //Костыль
                if (i == 2)
                    cancel()
            }
        }
        mainViewModel.onSearch(testString)
    }


    @After
    fun tearDown() {
        unmockkAll()
    }
}