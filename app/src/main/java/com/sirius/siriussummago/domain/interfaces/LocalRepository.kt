package com.sirius.siriussummago.domain.interfaces

interface LocalRepository {
    fun saveToken(token: String)
    fun removeToken()
    val token: String
}