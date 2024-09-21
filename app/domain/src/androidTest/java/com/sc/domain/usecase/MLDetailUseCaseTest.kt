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
class MLDetailUseCaseTest {
    @Mock
    private lateinit var repository: MLRepository

    private lateinit var useCase : MLDetailUseCase

    @Before
    fun setup() {
        useCase =  MLDetailUseCaseImp(repository)
    }

    @Test
    fun getDetailSuccessResult(): Unit = runBlocking {
        val item = getTestItem()
        Mockito.`when`(repository.getDetailResult(Mockito.anyString())).thenReturn(flowOf(item))

        val detailResult = useCase.getDetailResult("test")
        detailResult.collect{ result ->
            assertTrue(result is MLResultStatus.MLDetailResult)
            (result as MLResultStatus.MLDetailResult).let { detailItem ->
                assertEquals(detailItem.detail, item)
            }
        }
    }

    @Test
    fun getDetailErrorResult(): Unit = runBlocking {
        Mockito.`when`(repository.getDetailResult(Mockito.anyString())).thenReturn(flowOf(null))

        val detailResult = useCase.getDetailResult("test")
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