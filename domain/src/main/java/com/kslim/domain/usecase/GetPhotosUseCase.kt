package com.kslim.domain.usecase

import com.kslim.domain.model.Photo
import com.kslim.domain.repository.PhotoRepository
import com.kslim.domain.result.DataResult
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend fun execute(page: Int, perPage: Int = 20): DataResult<List<Photo>> {
        return photoRepository.getPhotos(page, perPage)
    }
}