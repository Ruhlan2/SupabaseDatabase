package com.ruhlan.supabasestorage.data.repository

import com.ruhlan.supabasestorage.data.common.utils.NetworkResource
import com.ruhlan.supabasestorage.data.dto.UserDto
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow

/**
 * Created on 30 Mar 2025,15:22
 * @author Ruhlan Usubov
 */
interface UserRepository {
    suspend fun createUser(userDto: UserDto): Flow<NetworkResource<PostgrestResult>>
    suspend fun getUsers(): Flow<NetworkResource<List<UserDto>>>
    suspend fun updateName(oldName:String,newName: String) : Flow<NetworkResource<PostgrestResult>>
}