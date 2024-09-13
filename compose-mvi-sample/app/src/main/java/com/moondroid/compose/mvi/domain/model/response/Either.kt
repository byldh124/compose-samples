package com.moondroid.compose.mvi.domain.model.response

sealed class Either<out T> {
    data class Success<out T>(val response: T) : Either<T>()
    data class Error<T>(val exception: Throwable) : Either<T>()
}

inline fun <T : Any> Either<T>.onSuccess(action: (T) -> Unit): Either<T> {
    if (this is Either.Success) action(response)
    return this
}

inline fun <T : Any> Either<T>.onError(action: (Throwable) -> Unit): Either<T> {
    if (this is Either.Error) action(exception)
    return this
}