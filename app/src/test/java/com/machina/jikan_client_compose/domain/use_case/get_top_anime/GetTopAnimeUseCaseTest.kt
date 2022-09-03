package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class GetTopAnimeUseCaseTest {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val animeRepository = mockk<AnimeRepository>()

  @ExperimentalCoroutinesApi
  private val testDispatchers = DefaultDispatchers(
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher()
  )

  @ExperimentalCoroutinesApi
  private val topAnimeUseCaseKtor by lazy { GetAnimeTopUseCase(animeRepository, testDispatchers) }


  @Test
  @ExperimentalCoroutinesApi
  fun `get top anime use case returns success`() = runBlockingTest {
    // Prepare
    val expectedResult = AnimeTopState(emptyList(), false, Event(null))

    coEvery {
      animeRepository.getAnimeTopOfAllTime(0)
    } returns Resource.Success(
      AnimeTopResponseV4()
    )

    // Execute
    val result = topAnimeUseCaseKtor().drop(1).first()

    // Assert
    assertThat(result.toString()).isEqualTo(expectedResult.toString())
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `get top anime use case returns error`() = runBlockingTest {
    // Prepare
    val errorMessage = "Something went wrong"
    val expectedResult = AnimeTopState(emptyList(), false, Event(errorMessage))

    coEvery { animeRepository.getAnimeTopOfAllTime(0) } returns Resource.Error(errorMessage)

    // Execute
    val result = topAnimeUseCaseKtor().drop(1).first()

    // Assert
    assertThat(result.toString()).isEqualTo(expectedResult.toString())
  }



}