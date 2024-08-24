package com.example.empresa1.data

data class Note(
    val id: Int = 0,
    val tittle: String,
    val text: String,
    val group: String,
    val rating: Int? = 0
)
