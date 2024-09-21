package com.sc.domain.usecase

import com.sc.domain.model.MLItemModel
import com.sc.domain.model.MLResultStatus
import com.sc.domain.repository.MLRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MLSearchUseCaseTest {
    @Mock
    private lateinit var repository: MLRepository

    private lateinit var useCase : MLSearchUseCase

    @Before
    fun setup() {
        useCase =  MLSearchUseCaseImp(repository)
    }

    @Test
    fun getSearchSuccessResult(): Unit = runBlocking {
        val item = getTestItem()
        Mockito.`when`(repository.getSearchResult(Mockito.anyString())).thenReturn(flowOf(listOf(item)))

        val detailResult = useCase.getSearchResult("test")
        detailResult.collect{ result ->
            assertTrue(result is MLResultStatus.MLSearchResult)
            (result as MLResultStatus.MLSearchResult).let { searchList ->
                assertEquals(searchList.list.size, 1)
                assertEquals(searchList.list[0], item)
            }
        }
    }

    @Test
    fun getSearchEmptyResult(): Unit = runBlocking {
        Mockito.`when`(repository.getSearchResult(Mockito.anyString())).thenReturn(flowOf(listOf()))

        val detailResult = useCase.getSearchResult("test")
        detailResult.collect{ result ->
            assertTrue(result is MLResultStatus.MLSearchResult)
            (result as MLResultStatus.MLSearchResult).let { searchList ->
                assertEquals(searchList.list.size, 0)
            }
        }
    }

    @Test
    fun getDetailErrorResult(): Unit = runBlocking {
        Mockito.`when`(repository.getSearchResult(Mockito.anyString())).thenReturn(flowOf(null))

        val detailResult = useCase.getSearchResult("test")
        detailResult.collect{ result ->
            assertTrue(result is MLResultStatus.MLError)
        }
    }

    private fun getTestItem() = MLItemModel(
        id = "1",
        title = "Motorola",
        price = 10.0f
    )
}