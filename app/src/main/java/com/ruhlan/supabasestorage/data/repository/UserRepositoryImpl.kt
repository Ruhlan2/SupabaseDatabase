package com.ruhlan.supabasestorage.data.repository

import com.ruhlan.supabasestorage.BuildConfig.SUPABASE_DB
import com.ruhlan.supabasestorage.data.common.utils.NetworkResource
import com.ruhlan.supabasestorage.data.common.utils.executeRequest
import com.ruhlan.supabasestorage.data.dto.UserDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created on 30 Mar 2025,15:23
 * @author Ruhlan Usubov
 */
class UserRepositoryImpl @Inject constructor(
    private val database: Postgrest
) : UserRepository {
    override suspend fun createUser(userDto: UserDto): Flow<NetworkResource<PostgrestResult>> =
        executeRequest {
            database.from(table = SUPABASE_DB).insert(userDto)
        }

    override suspend fun getUsers(): Flow<NetworkResource<List<UserDto>>> =
        executeRequest {
            database.from(table = SUPABASE_DB).select().decodeList<UserDto>()
        }

    override suspend fun updateName(oldName:String,newName: String): Flow<NetworkResource<PostgrestResult>> =
        executeRequest {
            database.from(table = SUPABASE_DB).update(
                {
                    UserDto::name setTo newName
                }
            ) {
                filter {
                    UserDto::name eq oldName
                }
            }
        }
}