package com.sirius.siriussummago.di

import com.sirius.siriussummago.presentation.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<SharedViewModel>{
        SharedViewModel(get())
    }
}