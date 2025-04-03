package com.point.user.storage

import com.point.user.token.Token

interface UserStorage {

    suspend fun updateToken(token: Token?)

    suspend fun getToken(): Token?

}