package com.example.my_application

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.my_application.feature_movielist.domain.usecase.get_shows.GetShowUseCase
import com.example.my_application.feature_movielist.presentation.shows.ShowsViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShowViewModelTest {

    val useCase: GetShowUseCase = mockk(relaxed = true, relaxUnitFun = true)
    private lateinit var viewmodel: ShowsViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewmodel = ShowsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getShowTest() {
        runBlocking {

            viewmodel.getShows()
            verify { useCase.invoke() }

        }
    }
}
