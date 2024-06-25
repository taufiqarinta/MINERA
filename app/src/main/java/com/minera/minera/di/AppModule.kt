package com.minera.minera.di

import com.minera.minera.presentation.signin.SignInViewModel
import com.minera.minera.presentation.signup.SignUpViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { SignUpViewModel(get()) }
    single { SignInViewModel(get()) }
}