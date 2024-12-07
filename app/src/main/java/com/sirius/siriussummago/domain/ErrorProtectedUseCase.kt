package com.sirius.siriussummago.domain

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

open class ErrorProtectedUseCase {
    protected val _errors = MutableSharedFlow<Error>()
    val errors: SharedFlow<Error> = _errors
}