package ru.point.core.services.auth.repository

import com.point.user.token.Token
import ru.point.core.services.auth.models.SigninData
import ru.point.core.services.auth.models.SignupData

interface AuthorizationRepository {

    suspend fun signIn(signinData: SigninData): Result<Token>

    suspend fun singUp(signupData: SignupData): Result<Token>

    suspend fun requestCode(email: String): Result<Unit>
}