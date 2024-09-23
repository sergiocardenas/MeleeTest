package com.sc.domain.repository

import com.sc.domain.model.MLResultStatus
import kotlinx.coroutines.flow.Flow

interface MLRepository {
    suspend fun getSearchResult(query: String): Flow<MLResultStatus>
    suspend fun getDetailResult(id: String): Flow<MLResultStatus>
}