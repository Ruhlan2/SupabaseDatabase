package com.ruhlan.supabasestorage.data.dto

import kotlinx.serialization.Serializable

/**
 * Created on 30 Mar 2025,15:17
 * @author Ruhlan Usubov
 */
@Serializable
data class UserDto(
    val name: String?,
    val email: String?
)