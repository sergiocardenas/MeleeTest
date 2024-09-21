package com.sc.domain.usecase

import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.flow.Flow

interface MLSearchUseCase {
    suspend fun getSearchResult(query: String): Flow<List<MLItemModel>>
}