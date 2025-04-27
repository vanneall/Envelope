package com.point.user.storage

import com.point.user.User
import com.point.user.token.Token
import kotlinx.coroutines.flow.Flow

interface UserStorage {

    suspend fun updateToken(token: Token?)

    suspend fun getToken(): Token?

    suspend fun tokenFlow(): Flow<Token>

    suspend fun getUser(): User?

    suspend fun setUser(user: User?)
}