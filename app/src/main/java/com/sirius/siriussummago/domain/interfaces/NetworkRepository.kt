package com.sirius.siriussummago.domain.interfaces

interface NetworkRepository {
    suspend fun getUserAuthState(token: String): Boolean?
    suspend fun signUp(token: String, name: String, organization: String, role: String): Boolean
}