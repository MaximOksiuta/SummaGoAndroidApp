package com.sirius.siriussummago.di

import com.sirius.siriussummago.domain.useCases.AuthUseCase
import org.koin.dsl.module

val domainModule = module {
    single <AuthUseCase>{
        AuthUseCase(get(), get())
    }
}