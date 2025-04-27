package com.point.user.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.point.user.User
import com.point.user.token.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

internal class UserStorageImpl(private val sharedPrefs: SharedPreferences) : UserStorage {

    override suspend fun updateToken(token: Token?) = withContext(Dispatchers.IO) {
        sharedPrefs.edit {
            if (token != null) {
                putString(KEY_TOKEN, token.value)
            } else {
                remove(KEY_TOKEN)
            }
        }
    }

    override suspend fun getToken() = withContext(Dispatchers.IO) {
        sharedPrefs.getString(KEY_TOKEN, null)?.let { Token(it) }
    }

    override suspend fun tokenFlow(): Flow<Token> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_TOKEN) {
                val token = sharedPrefs.getString(KEY_TOKEN, null)
                if (token != null) trySend(Token(token))
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose { sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override suspend fun getUser(): User? {
        val username = sharedPrefs.getString(KEY_USERNAME, null) ?: return null
        return User(username = username)
    }

    override suspend fun setUser(user: User?) {
        sharedPrefs.edit {
            if (user == null) {
                remove(KEY_USERNAME)
            } else {
                putString(KEY_USERNAME, user.username)
            }
        }
    }

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_USERNAME = "username"
    }
}