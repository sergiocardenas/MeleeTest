package com.sc.data.datasource

import com.sc.data.mocks.getMLItemResponseMock
import com.sc.data.mocks.getMLSearchResponseMock
import com.sc.data.response.MLResponseResult
import com.sc.data.response.MLSearchResponse
import com.sc.data.service.MercadoLibreService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class MLRemoteDataSourceTest{
    @Mock
    private lateinit var service: MercadoLibreService

    private lateinit var dataSource: MLRemoteDataSource

    @Before
    fun setup() {
        dataSource =  MLRemoteDataSourceImp(service)
    }

    @Test
    fun searchFromRemoteSuccess(): Unit = runBlocking {
        val searchResponse = getMLSearchResponseMock(
            listOf(getMLItemResponseMock(
                hasSalePrice = false,
                hasTags = false
            ))
        )

        val retrofitResponse = Response.success(searchResponse)

        Mockito.`when`(service.getSearch(Mockito.anyString())).thenReturn(retrofitResponse)

        val datasourceResult = dataSource.getSearchResult("test")
        val resultList = listOf(getMLItemResponseMock(
            hasSalePrice = false,
            hasTags = false
        ))

        datasourceResult.collect{response ->
            assertTrue(response is MLResponseResult.MLSearchResponseResult)
            val responseResult: MLResponseResult.MLSearchResponseResult = response as MLResponseResult.MLSearchResponseResult
            assertEquals(resultList.size, responseResult.list.size)
            assertEquals(resultList[0],  responseResult.list[0])
        }
    }
    @Test
    fun searchFromRemoteFailure(): Unit = runBlocking {
        val retrofitResponse = Response.error<MLSearchResponse>(500, "Error".toResponseBody())

        Mockito.`when`(service.getSearch(Mockito.anyString())).thenReturn(retrofitResponse)

        val datasourceResult = dataSource.getSearchResult("test")

        datasourceResult.collect{response ->
            assertTrue(response is MLResponseResult.MLResponseError)
        }
    }
}