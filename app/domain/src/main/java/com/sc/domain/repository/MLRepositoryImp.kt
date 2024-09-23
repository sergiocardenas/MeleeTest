package com.sc.domain.repository

import com.sc.data.datasource.MLRemoteDataSource
import com.sc.data.response.MLResponseResult
import com.sc.domain.mapper.toModel
import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MLRepositoryImp (
    private val dataSource: MLRemoteDataSource
) : MLRepository {
    override suspend fun getSearchResult(query: String): Flow<List<MLItemModel>?> {
        return dataSource.getSearchResult(query).map { itemResponse ->
            when(itemResponse){
                is MLResponseResult.MLSearchResponseResult ->{
                    itemResponse.list.map { it.toModel() }
                }
                else ->{
                    listOf()
                }
            }
        }
    }

    override suspend fun getDetailResult(id: String): Flow<MLItemModel?> {
        return dataSource.getDetailResult(id).map { itemResponse ->
            when(itemResponse){
                is MLResponseResult.MLDetailResponseResult ->{
                    itemResponse.detail.toModel()
                }
                else ->{
                    null
                }
            }
        }
    }
}