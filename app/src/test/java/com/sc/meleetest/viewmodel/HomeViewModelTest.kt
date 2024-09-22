package com.sc.meleetest.viewmodel

import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mock.getTestItem
import com.sc.meleetest.mock.getTestSearchItem
import com.sc.meleetest.utils.EMPTY_QUERY_MESSAGE
import com.sc.meleetest.utils.EMPTY_SEARCH_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var useCase: MLSearchUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchQuerySuccess(): Unit = runBlocking {
        val itemModel = getTestItem()
        val searchResponse = MLResultStatus.MLSearchResult(listOf(itemModel))
        val searchState = getTestSearchItem()

        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flowOf(searchResponse))

        viewModel.searchQuery("test")

        assertEquals(viewModel.list.value.size, searchResponse.list.size)
        assertEquals(viewModel.list.value[0].id, searchState.id)
        assertEquals(viewModel.list.value[0].title, searchState.title)
        assertEquals(viewModel.list.value[0].thumbnail, searchState.thumbnail)
        assertEquals(viewModel.list.value[0].currency_id, searchState.currency_id)
        assertEquals(viewModel.list.value[0].price, searchState.price)
        assertEquals(viewModel.list.value[0].original_price, searchState.original_price)
    }

    @Test
    fun searchQueryEmpty(): Unit = runBlocking {
        viewModel.searchQuery("")

        assertEquals(viewModel.errorMessage.value, EMPTY_QUERY_MESSAGE)
    }

    @Test
    fun searchQueryError(): Unit = runBlocking {
        val errorMessage = "test error"
        val searchResponse = MLResultStatus.MLError(errorMessage)

        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flowOf(searchResponse))

        viewModel.searchQuery("test")

        assertEquals(viewModel.list.value.size, 0)
        assertEquals(viewModel.errorMessage.value, errorMessage)
    }
}
