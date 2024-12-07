package com.sirius.siriussummago.domain.interfaces

interface LocalRepository {
    fun saveToken(token: String)
    val token: String
}