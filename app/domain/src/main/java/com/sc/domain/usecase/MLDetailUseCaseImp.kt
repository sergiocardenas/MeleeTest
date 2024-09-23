package com.sc.domain.usecase

import com.sc.domain.model.MLResultStatus
import com.sc.domain.repository.MLRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MLDetailUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLDetailUseCase{
    override suspend fun getDetailResult(id: String): Flow<MLResultStatus> =
        repository.getDetailResult(id)
}