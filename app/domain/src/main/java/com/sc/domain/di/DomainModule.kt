package com.sc.domain.di

import com.sc.data.datasource.MLRemoteDataSource
import com.sc.domain.repository.MLRepository
import com.sc.domain.repository.MLRepositoryImp
import com.sc.domain.usecase.MLDetailUseCase
import com.sc.domain.usecase.MLDetailUseCaseImp
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.domain.usecase.MLSearchUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideMLRepository(dataSource: MLRemoteDataSource): MLRepository = MLRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun provideMLSearchUseCase(repository: MLRepository): MLSearchUseCase =
        MLSearchUseCaseImp(repository)

    @Singleton
    @Provides
    fun provideMLDetailUseCase(repository: MLRepository): MLDetailUseCase =
        MLDetailUseCaseImp(repository)
}