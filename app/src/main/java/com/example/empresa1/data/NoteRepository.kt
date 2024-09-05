package com.example.empresa1.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getAllNotesStream(): Flow<List<Note>>

    fun getAllFavoritesStream(partName : String): Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note>

    fun lookingForNotesStream(partName : String): Flow<List<Note>>
}