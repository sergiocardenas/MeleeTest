package com.sc.domain.repository

import com.sc.data.datasource.MLRemoteDataSource
import com.sc.data.response.MLResponseResult
import com.sc.domain.mock.getMLItemResponseMock
import com.sc.domain.mock.getTestItem
import com.sc.domain.model.MLResultStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MLRepositoryTest{

    @Mock
    private lateinit var datasource : MLRemoteDataSource

    private lateinit var repository: MLRepository

    @Before
    fun setup() {
        repository = MLRepositoryImp(datasource)
    }


    @Test
    fun getSearchSuccessResult(): Unit = runBlocking {
        val item = getMLItemResponseMock(
            hasSalePrice = false,
            hasTags = false
        )
        Mockito.`when`(datasource.getSearchResult(Mockito.anyString())).thenReturn(
            flowOf(MLResponseResult.MLSearchResponseResult(listOf(item)))
        )

        val itemModel = getTestItem()
        val detailResult = repository.getSearchResult("test")
        detailResult.collect{ result ->
            Assert.assertTrue(result is MLResultStatus.MLSearchResult)
            (result as MLResultStatus.MLSearchResult).let { searchList ->
                Assert.assertEquals(searchList.list.size, 1)
                Assert.assertEquals(searchList.list[0].id, itemModel.id)
                Assert.assertEquals(searchList.list[0].title, itemModel.title)
                Assert.assertEquals(searchList.list[0].price, itemModel.price)
            }
        }
    }

    @Test
    fun getSearchEmptyResult(): Unit = runBlocking {
        Mockito.`when`(datasource.getSearchResult(Mockito.anyString())).thenReturn(
            flowOf(MLResponseResult.MLSearchResponseResult(listOf()))
        )

        val detailResult = repository.getSearchResult("test")
        detailResult.collect{ result ->
            Assert.assertTrue(result is MLResultStatus.MLSearchResult)
            (result as MLResultStatus.MLSearchResult).let { searchList ->
                Assert.assertEquals(searchList.list.size, 0)
            }
        }
    }

    @Test
    fun getSearchErrorResult(): Unit = runBlocking {
        val errorMsg = "test error"
        Mockito.`when`(datasource.getSearchResult(Mockito.anyString())).thenReturn(
            flowOf(MLResponseResult.MLResponseError(errorMsg))
        )

        val detailResult = repository.getSearchResult("test")
        detailResult.collect{ result ->
            Assert.assertTrue(result is MLResultStatus.MLError)
            (result as MLResultStatus.MLError).let { searchList ->
                Assert.assertEquals(searchList.message, errorMsg)
            }
        }
    }

    @Test
    fun getDetailSuccessResult(): Unit = runBlocking {
        val item = getMLItemResponseMock(
            hasSalePrice = false,
            hasTags = false
        )
        Mockito.`when`(datasource.getDetailResult(Mockito.anyString())).thenReturn(
            flowOf(MLResponseResult.MLDetailResponseResult(item))
        )
        val itemModel = getTestItem()

        val detailResult = repository.getDetailResult("test")
        detailResult.collect{ result ->
            Assert.assertTrue(result is MLResultStatus.MLDetailResult)
            (result as MLResultStatus.MLDetailResult).let { detailItem ->
                Assert.assertEquals(detailItem.detail.id, itemModel.id)
                Assert.assertEquals(detailItem.detail.title, itemModel.title)
                Assert.assertEquals(detailItem.detail.price, itemModel.price)
            }
        }
    }

    @Test
    fun getDetailErrorResult(): Unit = runBlocking {
        val errorMsg = "test error"
        Mockito.`when`(datasource.getDetailResult(Mockito.anyString())).thenReturn(
            flowOf(MLResponseResult.MLResponseError(errorMsg))
        )

        val detailResult = repository.getDetailResult("test")
        detailResult.collect{ result ->
            Assert.assertTrue(result is MLResultStatus.MLError)
            (result as MLResultStatus.MLError).let { searchList ->
                Assert.assertEquals(searchList.message, errorMsg)
            }
        }
    }

}