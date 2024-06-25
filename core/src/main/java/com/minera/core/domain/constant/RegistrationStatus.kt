package com.minera.core.domain.constant

sealed class RegistrationStatus(val status: Int){
    data object First: RegistrationStatus(1)
    data object Second: RegistrationStatus(2)
    data object Third: RegistrationStatus(3)
}