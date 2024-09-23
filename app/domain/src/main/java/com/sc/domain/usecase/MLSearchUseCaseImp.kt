package com.sc.domain.usecase

import com.sc.domain.model.MLResultStatus
import com.sc.domain.repository.MLRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MLSearchUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLSearchUseCase {
    override suspend fun getSearchResult(query: String): Flow<MLResultStatus> =
        repository.getSearchResult(query)
}