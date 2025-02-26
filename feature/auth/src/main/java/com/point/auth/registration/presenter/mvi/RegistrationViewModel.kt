package com.point.auth.registration.presenter.mvi

import com.point.viewmodel.MviViewModel

class RegistrationViewModel : MviViewModel<RegState, RegAction, Any>(
    initialValue = RegState()
) {
}