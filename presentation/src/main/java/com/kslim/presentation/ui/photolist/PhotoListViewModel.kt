package com.kslim.presentation.ui.photolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kslim.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {


    fun loadPhotos() {
        viewModelScope.launch {
            val result = getPhotosUseCase.execute(page = 1)

            Log.d("kslim", "result ${result}")
        }
    }
}