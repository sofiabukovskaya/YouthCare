package com.example.youthcare.repository.models

import java.util.*

data class UserBody(val date_of_birth: Date,
                    val email: String,
                    val first_name: String,
                    val last_name: String,
                    val gender: String,
                    val PasswordHash: String)
