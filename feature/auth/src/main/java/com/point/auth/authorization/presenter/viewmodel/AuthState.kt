package com.point.auth.authorization.presenter.viewmodel

import androidx.annotation.StringRes

data class AuthState(
    val login: String = "",
    val password: String = "",

    val isLoginInvalid: Boolean = false,
    @StringRes val loginInvalidReason: Int? = null,

    val isPasswordInvalid: Boolean = false,
    @StringRes val passwordInvalidReason: Int? = null,
)