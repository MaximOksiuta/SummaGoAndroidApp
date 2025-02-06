package com.sirius.siriussummago.di

import androidx.room.Room
import com.sirius.siriussummago.data.localDatabase.MainDao
import com.sirius.siriussummago.data.localDatabase.MainDatabase
import com.sirius.siriussummago.data.localstorage.SharedPrefs
import com.sirius.siriussummago.data.repositories.LocalRepositoryImpl
import com.sirius.siriussummago.data.repositories.NetworkRepositoryImpl
import com.sirius.siriussummago.domain.interfaces.LocalRepository
import com.sirius.siriussummago.domain.interfaces.NetworkRepository
import org.koin.dsl.module

val dataModule = module {

    single<NetworkRepository> {
        NetworkRepositoryImpl()
    }

    single<LocalRepository> {
        LocalRepositoryImpl(get())
    }

    single<SharedPrefs> {
        SharedPrefs(get())
    }

    single<MainDatabase> {
        Room.databaseBuilder(get(), MainDatabase::class.java, "main_db").build()
    }

    single<MainDao> {
        get<MainDatabase>().mainDao
    }
}