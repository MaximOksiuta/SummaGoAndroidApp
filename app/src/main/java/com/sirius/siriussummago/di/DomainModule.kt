package com.sirius.siriussummago.di

import com.sirius.siriussummago.domain.useCases.AuthUseCase
import com.sirius.siriussummago.domain.useCases.GetSummariesUseCase
import org.koin.dsl.module

val domainModule = module {
    single <AuthUseCase>{
        AuthUseCase(get(), get())
    }

    single<GetSummariesUseCase> {
        GetSummariesUseCase(get(), get())
    }
}