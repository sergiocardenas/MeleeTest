package com.sc.meleetest.viewmodel

import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLDetailUseCase
import com.sc.meleetest.mock.getTestDetailItem
import com.sc.meleetest.mock.getTestItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Mock
    private lateinit var useCase: MLDetailUseCase

    private lateinit var viewModel: SearchViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SearchViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchDetailSuccess(): Unit = runBlocking {
        val itemModel = getTestItem()
        val detailResponse = MLResultStatus.MLDetailResult(itemModel)
        val detailState = getTestDetailItem()

        Mockito.`when`(useCase.getDetailResult(Mockito.anyString()))
            .thenReturn(flowOf(detailResponse))

        viewModel.fetchDetail("01")

        assertNotNull(viewModel.detailItem.value)
        assertEquals(viewModel.detailItem.value!!.id, detailState.id)
        assertEquals(viewModel.detailItem.value!!.title, detailState.title)
        assertEquals(viewModel.detailItem.value!!.thumbnail, detailState.thumbnail)
        assertEquals(viewModel.detailItem.value!!.currency_id, detailState.currency_id)
        assertEquals(viewModel.detailItem.value!!.price, detailState.price)
        assertEquals(viewModel.detailItem.value!!.original_price, detailState.original_price)
        assertEquals(viewModel.detailItem.value!!.sold_quantity, detailState.sold_quantity)
        assertEquals(viewModel.detailItem.value!!.available_quantity, detailState.available_quantity)
        assertEquals(viewModel.detailItem.value!!.tags.size, detailState.tags.size)
    }

    @Test
    fun fetchDetailError(): Unit = runBlocking {
        val detailResponse = MLResultStatus.MLError("test error")

        Mockito.`when`(useCase.getDetailResult(Mockito.anyString()))
            .thenReturn(flowOf(detailResponse))

        viewModel.fetchDetail("01")

        assertNull(viewModel.detailItem.value)
    }
}
