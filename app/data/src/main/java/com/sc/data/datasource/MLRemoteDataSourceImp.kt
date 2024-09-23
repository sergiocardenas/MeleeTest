package com.sc.data.datasource

import com.sc.data.response.MLItemResponse
import com.sc.data.response.MLResponseResult
import com.sc.data.service.MercadoLibreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MLRemoteDataSourceImp @Inject constructor(
    private val service: MercadoLibreService
) : MLRemoteDataSource{
    override suspend fun getSearchResult(query: String): Flow<MLResponseResult> = flow {
        val list = withContext(Dispatchers.IO) {
            var searchList: List<MLItemResponse> = listOf()
            val result = service.getSearch(query)
            if(result.isSuccessful){
                if(result.body()!=null){
                    result.body()?.let {
                        searchList = it.results
                    }
                    MLResponseResult.MLSearchResponseResult(searchList)
                }else{
                    MLResponseResult.MLResponseError("Service without response")
                }

            }else{
                MLResponseResult.MLResponseError("Service Failed")
            }
        }
        emit(list)
    }

    override suspend fun getDetailResult(id: String): Flow<MLResponseResult> = flow {
        val detail = withContext(Dispatchers.IO) {
            MLResponseResult.MLResponseError("Service Failed")
        }
        emit(detail)
    }
}