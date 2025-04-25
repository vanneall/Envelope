package com.point.auth.registration.presenter.profile

import android.net.Uri

sealed interface RegProfileAction {

    sealed interface UiAction : RegProfileAction {

        data class OnPhotoPicked(val uri: Uri?) : UiAction

        data class OnNameInput(val value: String) : UiAction

        data class OnStatusInput(val value: String) : UiAction

        data class OnAboutInput(val value: String) : UiAction

    }
}