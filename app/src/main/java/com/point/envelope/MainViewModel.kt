package com.point.envelope

import androidx.lifecycle.ViewModel
import com.point.user.storage.UserStorage
import com.point.user.token.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userStorage: UserStorage) : ViewModel() {

    val token: Token?
        get() = runBlocking { userStorage.getToken() }

}