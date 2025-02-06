package com.sirius.siriussummago.data.repositories

import com.sirius.siriussummago.data.localstorage.SharedPrefs
import com.sirius.siriussummago.domain.interfaces.LocalRepository

class LocalRepositoryImpl(private val sharedPrefs: SharedPrefs) : LocalRepository {
    override val token: String
        get() = sharedPrefs.token

    override fun saveToken(token: String) {
        sharedPrefs.token = token
    }

    override fun removerToken() {
        saveToken("")
    }
}