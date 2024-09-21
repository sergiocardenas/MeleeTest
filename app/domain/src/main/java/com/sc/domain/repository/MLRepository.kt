package com.sc.domain.repository

import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.flow.Flow

interface MLRepository {
    suspend fun getSearchResult(query: String): Flow<List<MLItemModel>?>
    suspend fun getDetailResult(id: String): Flow<MLItemModel?>
}