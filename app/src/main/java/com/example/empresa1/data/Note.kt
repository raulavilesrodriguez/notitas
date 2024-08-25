package com.example.empresa1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tittle: String,
    val text: String,
    val topic: String,
    val favorite: Boolean = false,
    val rating: Int? = 0
)
