package com.minera.core.domain.model

import com.minera.core.domain.constant.RegistrationStatus
import java.util.Date
import java.util.UUID

data class  User(
    val id: String = UUID.randomUUID().toString().take(15),
    val username: String? = null,
    val name: String? = null,
    val email: String? = null,
    val tanggalLahir: String? = null,
    val jenisKelamin: String? = null,
    val noTelepon: String? = null,
    val registrationStatus: RegistrationStatus = RegistrationStatus.First,
    val createdAt: Date = Date()
)