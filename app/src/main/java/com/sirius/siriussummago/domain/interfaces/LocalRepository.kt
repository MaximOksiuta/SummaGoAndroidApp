package com.sirius.siriussummago.domain.interfaces

interface LocalRepository {
    fun saveToken(token: String)
    fun removerToken()
    val token: String
}