package com.kslim.domain.usecase

import com.kslim.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteIdsUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.observeFavoriteIds()
    }
}