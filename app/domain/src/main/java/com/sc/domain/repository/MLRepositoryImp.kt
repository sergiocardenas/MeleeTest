package com.sc.domain.repository

import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MLRepositoryImp (
) : MLRepository {
    override suspend fun getSearchResult(query: String): Flow<List<MLItemModel>?> {
        return flow { emit(listOf(MLItemModel()))  }
    }

    override suspend fun getDetailResult(id: String): Flow<MLItemModel?> {
        return flow { emit(MLItemModel()) }
    }
}