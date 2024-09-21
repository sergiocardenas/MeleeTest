package com.sc.domain.usecase

import com.sc.domain.repository.MLRepository
import javax.inject.Inject

class MLSearchUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLSearchUseCase {
    override suspend fun getSearchResult(query: String) = repository.getSearchResult(query)
}