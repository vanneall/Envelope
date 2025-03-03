package com.point.user.storage

import android.content.SharedPreferences
import androidx.core.content.edit

internal class UserStorageImpl(private val sharedPrefs: SharedPreferences) : UserStorage {

    override var token: String?
        get() = sharedPrefs.getString(KEY_TOKEN, null)
        set(value) {
            sharedPrefs.edit { putString(KEY_TOKEN, value) }
        }


    companion object {
        const val KEY_TOKEN = "token"
    }
}