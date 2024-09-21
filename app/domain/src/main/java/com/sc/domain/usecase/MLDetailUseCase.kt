package com.sc.domain.usecase

import com.sc.domain.model.MLItemModel
import kotlinx.coroutines.flow.Flow

interface MLDetailUseCase {
    suspend fun getDetailResult(id: String): Flow<MLItemModel>
}