package com.example.youthcare.repository.models

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("sportsmanUserId")
    var sportsmanId: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
)
