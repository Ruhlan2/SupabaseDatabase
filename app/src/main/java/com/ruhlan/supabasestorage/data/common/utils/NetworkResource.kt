package com.ruhlan.supabasestorage.data.common.utils

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

/**
 * Created on 30 Mar 2025,15:25
 * @author Ruhlan Usubov
 */
sealed interface NetworkResource<out R> {
    data class Success<R>(val data: R?) : NetworkResource<R>
    data object Loading : NetworkResource<Nothing>
    data class Error(val message: Throwable?) : NetworkResource<Nothing>
}

inline fun <R> executeRequest(crossinline request: suspend () -> R): Flow<NetworkResource<R>> =
    callbackFlow {
        trySend(NetworkResource.Loading)
        try {
            val response = request.invoke()
            trySend(NetworkResource.Success(response))
        } catch (e: Exception) {
            trySend(NetworkResource.Error(e))
        }
        awaitClose { trySend(NetworkResource.Success(null)) }
    }

fun <R> Flow<R>.mapResource(): Flow<NetworkResource<R>> = callbackFlow {
    onStart {
        trySend(NetworkResource.Loading)
    }.catch {
        trySend(NetworkResource.Error(message = it))
    }.collectLatest {
        trySend(NetworkResource.Success(data = it))
    }

    awaitClose {
        trySend(NetworkResource.Success(null))
    }
}


