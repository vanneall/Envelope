package com.point.envelope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.settings.model.AppSettings
import com.point.settings.repository.AppSettingsRepository
import com.point.user.User
import com.point.user.storage.UserStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userStorage: UserStorage,
    private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing: StateFlow<Boolean> = _isInitializing

    private val _localUser = MutableStateFlow<User?>(null)
    val localUser: StateFlow<User?> = _localUser

    private val _appSettings = MutableStateFlow(AppSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings

    init {
        viewModelScope.launch {
            launch { initUser() }
            launch { collectTokens() }
            launch { collectAppSettings() }
        }
    }

    private suspend fun initUser() {
        val user = userStorage.getUser()
            ?: userRepository.fetchUserDetailedInfo().fold(
                onSuccess = { user ->
                    Timber.tag("Login").d("Username: ${user.username}")
                    User(username = user.username)
                },
                onFailure = { t ->
                    Timber.tag("Error").e(t.localizedMessage)
                    null
                }
            )

        _localUser.value = user
        _isInitializing.value = false
        user?.let { userStorage.setUser(it) }
    }

    private suspend fun collectTokens() {
        userStorage.tokenFlow().collect {
            initUser()
        }
    }

    private suspend fun collectAppSettings() {
        appSettingsRepository.getSettings().collect { settings ->
            _appSettings.value = settings
        }
    }
}