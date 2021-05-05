package com.example.youthcare.repository.models

import com.google.gson.annotations.SerializedName

data class SportsmanUpdateResponse(
        @SerializedName("Name")
        var name: String,

        @SerializedName("Surname")
        var surname: String,

        @SerializedName("MiddleName")
        var middleName: String,

        @SerializedName("PhoneNumber")
        var phoneNumber: String,

        @SerializedName("Address")
        var address: String,

        @SerializedName("Email")
        var email: String,

        @SerializedName("UserName")
        var username: String,

        @SerializedName("UserType")
        var userType: String,

        @SerializedName("PasswordHash")
        var userPassword: String,
)



