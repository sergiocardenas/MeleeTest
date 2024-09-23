package com.sc.data.datasource

import com.sc.data.response.MLResponseResult
import kotlinx.coroutines.flow.Flow

interface MLRemoteDataSource {
    suspend fun getSearchResult(query: String): Flow<MLResponseResult>
    suspend fun getDetailResult(id: String): Flow<MLResponseResult>
}