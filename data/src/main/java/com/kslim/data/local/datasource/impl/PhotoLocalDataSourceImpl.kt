package com.kslim.data.local.datasource.impl

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.kslim.data.local.dao.FavoritePhotoDao
import com.kslim.data.local.datasource.PhotoLocalDataSource
import com.kslim.data.local.entity.FavoritePhotoEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class PhotoLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val favoritePhotoDao: FavoritePhotoDao
) : PhotoLocalDataSource {
    override fun observeFavoriteIds(): Flow<List<String>> {
        return favoritePhotoDao.observeFavoriteIds()
    }

    // 관심 (좋아요) 화면 내 리스트 구성할 때, 저장된 사진만 보여줌 ( 존재 하지 않으면 미노출 )
    override fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>> {
        return favoritePhotoDao.observeFavoritePhotos().map { photos ->
            val invalidPhotos = photos
                .filter { photo ->
                    photo.localPath.isNullOrEmpty() || !isLocalImageExists(photo.localPath)
                }.map { it.id }
                .toSet()

            photos.filterNot { photo ->
                photo.id in invalidPhotos
            }
        }
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoritePhotoDao.getFavoriteIds()
    }

    override suspend fun getFavoritePhoto(photoId: String): FavoritePhotoEntity? {
        return favoritePhotoDao.getFavoritePhoto(photoId)
    }

    override suspend fun insertFavorite(photo: FavoritePhotoEntity) {
        favoritePhotoDao.insert(photo)
    }

    override suspend fun updateFavorite(photoId: String, isFavorite: Boolean) {
        favoritePhotoDao.updateFavorite(photoId, isFavorite)
    }

    override suspend fun updateLocalPath(photoId: String, path: String, isFavorite: Boolean) {
        favoritePhotoDao.updateLocalPath(photoId, path, isFavorite)
    }

    override suspend fun savePhoto(photoName: String, bytes: ByteArray): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveToPublicDownloadsByMediaStore(photoName, bytes)
        } else {
            saveToLegacyPublicDownloads(photoName, bytes)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveToPublicDownloadsByMediaStore(
        photoName: String, bytes: ByteArray
    ): String? {
        val resolver = context.contentResolver

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, photoName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/PhotoExplorer"
            )
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                  ?: return null

        return try {
            resolver.openOutputStream(uri)?.use { output ->
                output.write(bytes)
            } ?: return null

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)

            uri.toString()
        } catch (t: Throwable) {
            resolver.delete(uri, null, null)
            null
        }
    }

    @Suppress("DEPRECATION")
    private fun saveToLegacyPublicDownloads(
        photoName: String, bytes: ByteArray
    ): String? {
        return runCatching {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            val appDir = File(picturesDir, "PhotoExplorer")
            if (!appDir.exists()) {
                appDir.mkdirs()
            }

            val file = File(appDir, photoName)

            file.outputStream().use { output ->
                output.write(bytes)
            }

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DATA, file.absolutePath)
                put(MediaStore.Images.Media.DISPLAY_NAME, photoName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            MediaScannerConnection.scanFile(
                context,
                arrayOf(file.absolutePath),
                arrayOf("image/jpeg"),
                null
            )
            uri?.toString() ?: file.absolutePath
        }.getOrNull()
    }

    private fun isLocalImageExists(localPath: String): Boolean {
        return runCatching {
            if (localPath.startsWith("content://")) {
                context.contentResolver.openInputStream(localPath.toUri())
                    ?.use {
                        true
                    }
                ?: false
            } else {
                File(localPath).exists()
            }
        }.getOrDefault(false)
    }
}
