package com.point.tea

sealed interface LoadableState<T> {

    operator fun plus(other: LoadableState<T>): LoadableState<T>

    data class Loading<T, H : StateHolder<T>>(val holder: H? = null) : LoadableState<T> {

        override fun plus(other: LoadableState<T>): LoadableState<T> = when (other) {
            is Loading<*, *> -> if (other.holder != null) other else this as LoadableState<T>

            else -> this
        }
    }

    sealed interface StateHolder<T> : LoadableState<T> {

        data class Success<T>(val data: T) : StateHolder<T> {

            override fun plus(other: LoadableState<T>): LoadableState<T> = other
        }

        data class Error<T>(val data: T) : StateHolder<T> {

            override fun plus(other: LoadableState<T>): LoadableState<T> = when (other) {
                is Success -> this

                else -> other
            }
        }
    }
}