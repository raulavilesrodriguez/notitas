package com.example.empresa1.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tittle: String ="",
    val text: String = "",
    val topic: String = "Otros",
    val favorite: Boolean = false,
    val rating: Int = 0,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val created: String? = ""
)
