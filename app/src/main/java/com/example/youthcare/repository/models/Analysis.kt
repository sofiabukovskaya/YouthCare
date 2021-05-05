package com.example.youthcare.repository.models

data class Analysis(
    val id: String, val doctorName: String, val analysisType: String, val measure: Double, val height: Int, val weight: Int, val description: String,
    val result: String)
