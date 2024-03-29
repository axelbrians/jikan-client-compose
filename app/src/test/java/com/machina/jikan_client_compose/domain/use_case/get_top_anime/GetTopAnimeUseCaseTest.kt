package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopResponseV4
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class GetTopAnimeUseCaseTest {

//	@get:Rule
//	val rule = InstantTaskExecutorRule()

	private val animeRepository = mockk<AnimeService>()

//	@ExperimentalCoroutinesApi
//	private val testDispatchers = DefaultDispatchers(
//		TestCoroutineDispatcher(),
//		TestCoroutineDispatcher(),
//		TestCoroutineDispatcher(),
//		TestCoroutineDispatcher(),
//		TestCoroutineDispatcher()
//	)

//	@ExperimentalCoroutinesApi
//	private val topAnimeUseCaseKtor by lazy { GetAnimeTopUseCase(animeRepository, testDispatchers) }


	@Test
	fun `get top anime use case returns success`() = runTest {
		// Prepare
//		val expectedResult = AnimeTopState(emptyList(), false, Event(null))
//
//		coEvery {
//			animeRepository.getAnimeTopOfAllTime(0)
//		} returns Resource.Success(
//			AnimeTopResponseV4()
//		)

		// Execute
//    val result = topAnimeUseCaseKtor().drop(1).first()

		// Assert
//    assertThat(result.toString()).isEqualTo(expectedResult.toString())
	}

	@Test
	fun `get top anime use case returns error`() = runTest {
		// Prepare
//		val errorMessage = "Something went wrong"
//		val expectedResult = AnimeTopState(emptyList(), false, Event(errorMessage))
//
//		coEvery { animeRepository.getAnimeTopOfAllTime(0) } returns Resource.Error(errorMessage)

		// Execute
//    val result = topAnimeUseCaseKtor().drop(1).first()

		// Assert
//    assertThat(result.toString()).isEqualTo(expectedResult.toString())
	}


}