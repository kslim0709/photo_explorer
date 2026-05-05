package com.kslim.domain.usecase

import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.repository.PhotoRepository
import com.kslim.domain.result.DataResult
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend fun execute(photo: FavoritePhoto): DataResult<Unit> {
        return repository.toggleFavorite(photo)
    }
}