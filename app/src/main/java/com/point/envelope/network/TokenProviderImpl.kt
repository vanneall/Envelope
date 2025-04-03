package com.point.envelope.network

import com.point.network.di.TokenProvider
import com.point.user.storage.UserStorage
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenProviderImpl @Inject constructor(private val user: UserStorage) : TokenProvider {

    override val token: String?
        get() = runBlocking { user.getToken() }?.value
}