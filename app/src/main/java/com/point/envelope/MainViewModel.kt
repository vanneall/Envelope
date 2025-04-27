package com.point.envelope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing: StateFlow<Boolean> = _isInitializing

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch { initUser() }
    }

    private suspend fun initUser() {
        userRepository.fetchUserDetailedInfo().fold(
            onSuccess = { user ->
                _user.value = User(
                    username = user.username,
                )
            },
            onFailure = { t ->
                Timber.tag("Error").e(t.localizedMessage)
                _user.value = null
            }
        )
        _isInitializing.value = false
    }
}