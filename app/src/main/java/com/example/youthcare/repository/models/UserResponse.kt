package com.example.youthcare.repository.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("surname")
    var surname: String,

    @SerializedName("middleName")
    var middleName: String,

    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("userName")
    var username: String)
