package com.point.chats.gallery

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _photos = mutableStateOf<List<File>>(emptyList())
    val photos: State<List<File>> = _photos

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        val files: List<File> = context.cacheDir
            .listFiles { file -> file.name.startsWith("IMG_") && file.name.endsWith(".jpg") }
            ?.sortedByDescending { it.lastModified() } ?: emptyList()

        _photos.value = files
    }

    fun deletePhoto(file: File) {
        file.delete()
        loadPhotos()
    }
}
