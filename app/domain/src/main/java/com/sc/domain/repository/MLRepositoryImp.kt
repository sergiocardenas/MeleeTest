package com.sc.domain.repository

import com.sc.data.datasource.MLRemoteDataSource
import com.sc.data.response.MLResponseResult
import com.sc.domain.mapper.toModel
import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MLRepositoryImp (
    private val dataSource: MLRemoteDataSource
) : MLRepository {
    override suspend fun getSearchResult(query: String): Flow<List<MLItemModel>?> {
        return dataSource.getSearchResult(query).map { itemResponse ->
            when(itemResponse){
                is MLResponseResult.MLSearchResponseResult ->{
                    (itemResponse).list.map { it.toModel() }
                }
                else ->{
                    listOf()
                }

            }


        }
    }

    override suspend fun getDetailResult(id: String): Flow<MLItemModel?> {
        return flow {
            delay(1000)
            emit(MLItemModel(
            id = "1",
            title = "Motorola",
            condition = "new",
            thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
            currency_id = "USD",
            price = 10.0f,
            original_price = 10.0f,
            sold_quantity = 1,
            available_quantity = 0,
            tags = listOf("phone"),
        )) }
    }
}