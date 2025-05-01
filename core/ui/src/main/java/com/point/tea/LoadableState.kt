package com.point.tea

import com.point.tea.LoadableState.DataHolder
import com.point.tea.LoadableState.Loading

sealed class LoadableState<T> {

    internal data class Loading<T>(val previous: DataHolder<T>? = null) : LoadableState<T>()

    internal sealed class DataHolder<T> : LoadableState<T>() {

        data class Success<T>(val data: T) : DataHolder<T>()

        data class Error<T>(val error: Throwable?) : DataHolder<T>()
    }

    companion object {

        fun <T> loadingOf(data: T? = null): LoadableState<T> = Loading(data?.let { DataHolder.Success(it) })

        fun <T> successOf(data: T): LoadableState<T> = DataHolder.Success(data)

        fun <T> errorOf(throwable: Throwable): LoadableState<T> = DataHolder.Error<T>(throwable)
    }

    val isDirtyLoading: Boolean
        get() = this is Loading && this.previous != null

    val isClearLoading: Boolean
        get() = this is Loading && this.previous == null

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is DataHolder.Success

    val isError: Boolean
        get() = this is DataHolder.Error
}


fun <T> LoadableState<T>.mapSuccess(block: (state: T) -> T): LoadableState<T> = when (this) {
    is DataHolder.Success -> this.copy(data = block(data))
    else -> this
}

suspend fun <T> LoadableState<T>.doOnSuccess(block: suspend (state: T) -> Unit) {
    if (this is DataHolder.Success) {
        block(data)
    }
}

/***
 * Get saved data from Loadable
 * It will get from DataHolder or saved DataHolder
 */
val <T> LoadableState<T>.dataOrNull: T?
    get() = when {
        this is DataHolder.Success -> data
        this is Loading && previous is DataHolder.Success -> previous.data
        else -> null
    }

/***
 * Converts current Loadable to Loading
 * If current Loadable contains data, it will passed to Loading
 */
fun <T> LoadableState<T>.toLoading() = if (this is DataHolder) Loading(this) else this

fun <T> LoadableState<T>.toSuccess(default: T, transform: (T) -> T): LoadableState<T> = when {
    this is DataHolder.Success -> this.mapSuccess(transform)
    this is Loading && previous is DataHolder.Success -> LoadableState.successOf(transform(previous.data))
    else -> LoadableState.successOf(transform(default))
}
