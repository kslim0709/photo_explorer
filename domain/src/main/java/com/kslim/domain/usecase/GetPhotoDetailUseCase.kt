package com.kslim.domain.usecase

import com.kslim.domain.model.PhotoDetail
import com.kslim.domain.repository.PhotoRepository
import com.kslim.domain.result.DataResult
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend fun execute(photoId: String): DataResult<PhotoDetail> {
        return repository.getPhotoDetail(photoId)

    }
}