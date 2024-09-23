package com.sc.domain.repository

import com.sc.data.datasource.MLRemoteDataSource
import com.sc.data.response.MLResponseResult
import com.sc.domain.mapper.toModel
import com.sc.domain.model.MLResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MLRepositoryImp (
    private val dataSource: MLRemoteDataSource
) : MLRepository {
    override suspend fun getSearchResult(query: String): Flow<MLResultStatus> {
        return dataSource.getSearchResult(query).map { itemResponse ->
            when(itemResponse){
                is MLResponseResult.MLSearchResponseResult ->{
                    MLResultStatus.MLSearchResult(itemResponse.list.map { it.toModel() })
                }
                is MLResponseResult.MLResponseError ->{
                    MLResultStatus.MLError(itemResponse.message)
                }
                else ->{
                    MLResultStatus.MLError("no search found")
                }
            }
        }
    }

    override suspend fun getDetailResult(id: String): Flow<MLResultStatus> {
        return dataSource.getDetailResult(id).map { itemResponse ->
            when(itemResponse){
                is MLResponseResult.MLDetailResponseResult ->{
                    MLResultStatus.MLDetailResult(itemResponse.detail.toModel())
                }
                is MLResponseResult.MLResponseError ->{
                    MLResultStatus.MLError(itemResponse.message)
                }
                else ->{
                    MLResultStatus.MLError("No item found")
                }
            }
        }
    }
}