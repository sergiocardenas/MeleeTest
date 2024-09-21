package com.sc.domain.usecase

import com.sc.domain.repository.MLRepository
import javax.inject.Inject

class MLDetailUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLDetailUseCase{
    override suspend fun getDetailResult(id: String) = repository.getDetailResult(id)
}