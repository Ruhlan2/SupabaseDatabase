package com.ruhlan.supabasestorage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruhlan.supabasestorage.data.common.utils.NetworkResource
import com.ruhlan.supabasestorage.data.dto.UserDto
import com.ruhlan.supabasestorage.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created on 30 Mar 2025,15:32
 * @author Ruhlan Usubov
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _createUserResult =
        MutableStateFlow<NetworkResource<PostgrestResult>>(NetworkResource.Loading)
    val createUserResult: StateFlow<NetworkResource<PostgrestResult>>
        get() = _createUserResult.asStateFlow()

    private val _usersResult =
        MutableStateFlow<NetworkResource<List<UserDto>>>(NetworkResource.Loading)
    val userResult: StateFlow<NetworkResource<List<UserDto>>> = _usersResult.asStateFlow()

    private val _updateSelectedNameResult = MutableStateFlow<NetworkResource<PostgrestResult>>(NetworkResource.Loading)
    val updateSelectedNameResult: StateFlow<NetworkResource<PostgrestResult>> = _updateSelectedNameResult.asStateFlow()

    init {
        getAllUsers()
    }

    fun createUser(
        email: String,
        name: String
    ) {
        viewModelScope.launch {
            userRepository.createUser(
                userDto = UserDto(
                    name = name,
                    email = email
                )
            ).collect {
                _createUserResult.value = it
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect {
                _usersResult.value = it
            }
        }
    }

    fun updateSelectedName(
        oldName: String,
        newName: String
    ) {
        viewModelScope.launch {
            userRepository.updateName(
                oldName = oldName,
                newName = newName
            ).collect {
                _updateSelectedNameResult.value = it
            }
        }
    }
}