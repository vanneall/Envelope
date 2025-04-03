package com.point.user.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.point.user.token.Token
import kotlinx.coroutines.Dispatchers
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

    companion object {
        const val KEY_TOKEN = "token"
    }
}