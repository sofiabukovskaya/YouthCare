package com.example.youthcare.repository.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("accessToken")
        var authToken: String,

        @SerializedName("status_code")
        var statusCode: Int,

        @SerializedName("user")
        var user: UserBody
)
