package com.example.youthcare.repository.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class UserBody(
    val name: String,
    val surname: String,
    val gender: String,
    val username: String,
    val birthDate: LocalDate,
    val email: String,
    val passwordHash: String,
    val belongSection: String,
    val userType: String
)
