package com.example.youthcare.repository.models

import com.google.gson.annotations.SerializedName

data class NoteData(
    @SerializedName("SportsmanUserId")
    var sportsmanId: String,
    @SerializedName("Title")
    var title: String,
    @SerializedName("Description")
    var description: String,
)
