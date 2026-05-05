package com.kslim.domain.usecase

import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// 관심 목록 리스트
class ObserveFavoritePhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(): Flow<List<FavoritePhoto>> {
        return repository.observeFavoritePhotos()
    }
}